var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);



io.on('connection', function(socket) {
    console.log('a user connection');

    socket.on('disconnect', function(){
        console.log('user disconnect');
    });
    
    socket.on('chat', function(msg) {
        //io.emit('chat', msg); // tum kullanicilara gonderir mesaji
        // socket.broadcast.to(id).emit('chat', msg);
        //io.to(socket.rooms[1]).emit('chat', msg);
        io.to(msg.room).emit('chat', msg);
    });

    socket.on('room', function(room) {
        socket.join(room);
    });
});


http.listen(3000, function() {
    console.log('Listening on *:3000');
});
