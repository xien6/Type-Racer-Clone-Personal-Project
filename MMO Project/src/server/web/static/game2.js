var socket = io.connect({transports: ['websocket']});

socket.on('gameState', parseGameState);
function parseGameState(jsonGameState){
    const gameState = JSON.parse(jsonGameState);
    const text = gameState["text"];
    const leaderboard = gameState["leaderBoard"];
    document.getElementById("Leaderboard").value = leaderboard;
    document.getElementById("Sample Text").value = text;
    const playerText = document.getElementById("Typed Text").value
    socket.emit("sendPlayerText", playerText);
}

function ParseName(){
    const username = document.getElementById("Username").value;
    if (username === ""){
        alert("Please Enter a Username");
    }else{
        console.log(username)
        socket.emit("createUser", username);
    }
}
