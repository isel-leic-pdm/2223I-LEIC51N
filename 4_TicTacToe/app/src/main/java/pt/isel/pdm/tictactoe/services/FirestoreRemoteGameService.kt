package pt.isel.pdm.tictactoe.services

import android.content.Context
import android.provider.Settings
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import pt.isel.pdm.tictactoe.model.Lobby
import java.io.InvalidObjectException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirestoreRemoteGameService(
    ctx: Context
) : RemoteGameService {

    private val _db by lazy { Firebase.firestore }
    private val _uniqueId by lazy {
        Settings.Secure.getString(
            ctx.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }


    companion object {
        const val LOBBY_COLLECTION = "lobbies"
        const val GAME_COLLECTION = "games"
        const val LOBBY_CONNECTION_RETRY_DELAY = 2000L
        const val LOBBY_DISPLAY_NAME = "displayName"
        const val LOBBY_PLAYER2_NAME = "player2Name"
        const val GAME_PLAYER1 = "player1"
        const val GAME_PLAYER2 = "player2"
        const val LOG = "FirebaseLobby"
    }

    override suspend fun getLobbies(): List<Lobby> {
        val lobbies = _db.collection(LOBBY_COLLECTION).get().await()

        return lobbies.map {
            FirestoreRemoteLobby(
                it.getString(LOBBY_DISPLAY_NAME)!!,
                it.id,
            )
        }
    }

    override suspend fun create(name: String): Lobby {
        var lobby = hashMapOf(
            LOBBY_DISPLAY_NAME to name
        )
        var doc = _db.collection(LOBBY_COLLECTION).add(lobby).await()
        return FirestoreRemoteLobby(name, doc.id)
    }

    suspend fun waitForPlayerpoll(l: Lobby) {
        val lobby = l as FirestoreRemoteLobby
        var player2Id: String? = null
        do {
            val doc = _db
                .collection(LOBBY_COLLECTION)
                .document(lobby.lobbyId)
                .get()
                .await()

            player2Id = doc.getString(LOBBY_PLAYER2_NAME)

            if (player2Id.isNullOrEmpty() == false)
                break

            delay(LOBBY_CONNECTION_RETRY_DELAY)
        } while (true)

        lobby.gameDocId = player2Id

        var gameData = hashMapOf(
            GAME_PLAYER1 to lobby.displayName
        )
        _db.collection(GAME_COLLECTION)
            .document(lobby.gameDocId!!)
            .set(gameData)
            .await()

        remove(l)
    }

    override suspend fun waitForPlayer(l: Lobby) {
        val lobby = l as FirestoreRemoteLobby

        val player2 = waitForDocChange<String?>(
            lobby,
            _db.collection(LOBBY_COLLECTION).document(lobby.lobbyId),
        ) { doc ->
            if (!doc.exists()) {
                throw Exception("Lobby cannot be found")
            }

            val otherPlayer = doc.getString(LOBBY_PLAYER2_NAME)

            //
            //  Some change happen but no player 2 entered, continue
            //
            if (otherPlayer.isNullOrEmpty())
                return@waitForDocChange null

            return@waitForDocChange otherPlayer


        }

        lobby.gameDocId = player2

        var gameData = hashMapOf(
            GAME_PLAYER2 to lobby.gameDocId
        )
        _db.collection(GAME_COLLECTION).document(lobby.gameDocId!!).set(gameData).await()

        remove(l)


    }

    private suspend fun <T> waitForDocChange(
        lobby: FirestoreRemoteLobby,
        document: DocumentReference,
        pred: (DocumentSnapshot) -> T
    ): T {
        try {
            return suspendCancellableCoroutine<T> { continuation ->

                lobby.listener = document.addSnapshotListener { snapshot, err ->
                    if (err != null) {
                        Log.e(LOG, "${err.code} - ${err.message}")
                        continuation.resumeWithException(Exception("${err.message}"))
                    }
                    if (snapshot == null)
                        continuation.resumeWithException(Exception("Lobby cannot be found"))
                    try {
                        val res = pred(snapshot!!)
                        if (res != null)
                            continuation.resume(res)

                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                }
            }
        } finally {
            clearLobbyListener(lobby)
            Log.d("TAG", "CANCELLED")
        }

    }


    override suspend fun join(l: Lobby) {
        val lobby = l as FirestoreRemoteLobby
        var fireStoreLobby = _db.collection(LOBBY_COLLECTION).document(lobby.lobbyId)

        val remoteLobby = fireStoreLobby.get().await()

        if (remoteLobby == null || remoteLobby.exists() == false)
            throw InvalidObjectException("Error waiting for lobby")

        fireStoreLobby
            .update(LOBBY_PLAYER2_NAME, _uniqueId)
            .await()

        waitForDocChange(lobby, fireStoreLobby) { doc ->
            if (doc.exists())
                return@waitForDocChange null

            return@waitForDocChange Unit
        }


        val gameDoc = _db.collection(GAME_COLLECTION).document(_uniqueId).get().await()

        if (gameDoc == null || gameDoc.exists() == false)
            throw InvalidObjectException("Lobby join failed")

        lobby.gameDocId = _uniqueId
    }

    override suspend fun remove(l: Lobby) {
        val lobby = l as FirestoreRemoteLobby

        clearLobbyListener(lobby)
        _db.collection(LOBBY_COLLECTION).document(lobby.lobbyId).delete().await()
    }

    private fun clearLobbyListener(lobby: FirestoreRemoteLobby) {
        if (lobby.listener == null)
            return

        lobby.listener!!.remove()
        lobby.listener = null
    }

    private data class FirestoreRemoteLobby(
        override val displayName: String,
        val lobbyId: String,
        var listener: ListenerRegistration? = null,
        var gameDocId: String? = null
    ) : Lobby

}