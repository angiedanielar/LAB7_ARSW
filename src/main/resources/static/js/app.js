var app = (function () {

    var _cinema;
    var _date;
    var _module = "js/apiclient.js";
    var _hour;
    var _funcion;
    var fun = null;
    var stompClient = null;
    var seats;
    var canvas;
    var concatenacion;

    class Seat {
        constructor(row, col) {
            this.row = row;
            this.col = col;
        }
    }

    var connectAndSubscribe = function (identificador, callback) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);

        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/buyticket'+identificador,function (message) {
                callback(message);

            });
        });

    };

    var verifyAvailability = function (row,col) {
        var st = new Seat(row, col);
        if (seats[row][col]===true) {
            seats[row][col] = false;
            alert("Purchased ticket");
            stompClient.send("/topic/buyticket" + concatenacion, {}, JSON.stringify(st));

        }

    };

    function lanzaEvento(evento) {
        var theObject = JSON.parse(evento.body);
        console.info(theObject);
        pintarAsiento(theObject.row, theObject.col);

    }

    function pintarAsiento(fila,columna){
        var c = document.getElementById("availabilityCanvas");
        var ctx = c.getContext("2d");
        ctx.fillStyle = "#4287f5";
        ctx.fillRect((columna) * 49 + 9, (fila) * 54 + 120, 40, 40);
    }

    function getMousePosition() {
        $('#availabilityCanvas').click(function (e) {
            var rect = canvas.getBoundingClientRect();
            var x = e.clientX - rect.left;
            var y = e.clientY - rect.top;
            esAsiento(x,y);
        });

    };

    function esAsiento(x,y){
        var c = document.getElementById("availabilityCanvas");
        var ctx = c.getContext("2d");
        const pixel = ctx.getImageData(x, y, 1, 1).data;

        if (!(pixel[0] == 0 && pixel[1] == 0 && pixel[2] == 0 && pixel[3] == 255)){
            if (pixel[0] == 66 && pixel[1] == 135 && pixel[2] == 245) {
                alert("Seat occupied");
            }
            else {
                alert("Please click a seat")
            }
        }
        else {

            calcularAsiento(x, y);
        }
    };

    function calcularAsiento(x,y){    	        
        var row;
        var col;
        var limiteX1;
        var limiteY1;
        var limiteX2;
        var limiteY2
        for (var i = 0; i < 12; i++) {
            for (var j = 0; j < 7; j++) {
                limiteX1 = i * 50 + 15;
                limiteY1 = j * 54 + 125;
                limiteX2 = limiteX1 + 20;
                limiteY2 = limiteY1 + 20;
                if (x >= limiteX1 && x <= limiteX2 && y >= limiteY1 && y <= limiteY2) {
                    row = j;
                    col = i;
                }
            }
        }
        verifyAvailability(row, col);
    }

    function _mapOneByOne(cinemaFunctions) {
        $("#cines > tbody").empty();
        var tab = $("<table></table>")

        cinemaFunctions.forEach((f) => {
            console.log(f);
            var column = $("<tr>");
            column.append($("<td>" + f.movie.name + "</td>"));
            column.append($("<td>" + f.movie.genre + "</td>"));
            column.append($("<td>" + f.date.split(" ")[1] + "</td>"));
            var but = $("<button class='btn btn-outline-primary'>Open Seats</button>");
            var td = $("<td></tr>");
            td.append(but);
            column.append(td);
            but.click(() => {
                fun = f;
                openSeats(f.movie.name, f.date.split(" ")[0]);
            });
            $("#cines > tbody").append(column);
        });
        $("#cines").append(tab);
    }

    function getFunctions() {
        _cinema = $("#cinema").val();
        _date = $("#date").val();
        if (_cinema === "" || _date === "") {
            alert("Cinema name and date are required");
        } else {
            $("#cinemaSelected").text("Cinema Selected: " + _cinema);
            apiclient.getFunctionsByCinemaAndDate(_cinema, _date, _mapOneByOne);
        }
    }

    function openSeats(functionName, hour) {
        _funcion = functionName;
        _hour = hour;
        _cinema = $("#cinema").val();
        $("#availabilityOf").text("Availabilty of: " + functionName);
        document.getElementById('availabilityInfo').style.visibility = "visible";
        $.getScript(_module, function () {
            apiclient.getFunctionByFunctionNameAndDate(_cinema, _date + " " + _hour, _funcion, _updateCanvas);
        });
        concatenacion = "."+_cinema + "." + _date + "." + _funcion;
        connectAndSubscribe(concatenacion, lanzaEvento);
    }

    function _updateCanvas(f) {
        if (f != null) {
            clearCanvas();
            console.log(fun);
            seats = fun.seats;
            console.log(seats);
            var c = document.getElementById("availabilityCanvas");
            var ctx = c.getContext("2d");
            ctx.fillStyle = "#969693";
            //Pantalla ctx.fillRect(c.width * 0.2, c.height * 0.05, c.width * 0.6, c.height * 0.075);
            var x = c.width * 0.03;            
            var y = c.height * 0.031;
            var w = (c.width) * 0.75;
            var h = (c.height) * 0.75;
            var l;
            if (w < h) {
                l = w * 0.91 / seats[0].length;
            } else {
                l = h * 0.91 / seats.length;
            }
            var dx = (w - (l * seats[0].length)) / seats[0].length;
            var dy = (h - (l * seats.length)) / seats.length;

            seats.map(function (row) {
                x = c.width * 0.02;
                row.map(function (seat) {
                    ctx.fillStyle = "#000000";
                    if (!seat) {
                        ctx.fillStyle = "#4287f5";
                    }
                    ctx.fillRect(x, y, l, l);
                    x = x + 50 + dx;
                })
                y = y + 50 + dy;
            });

        }
    }

    function clearCanvas() {
        canvas = document.getElementById("availabilityCanvas");
        var ctx = canvas.getContext("2d");
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.beginPath();
    }


    function update() {
        fun.date = _date + " " + $("#hour").val();
        _hour = $("#hour").val();
        _date = $("#date").val();
        if (_hour === "") {
            alert('Please specify an hour')
        } else {
            console.log(fun);
            $.getScript(_module, function () {
                apiclient.saveUpdateFunction(_cinema, fun, _actualizar);
            });
        }
    }

    function _actualizar() {
        $.getScript(_module, function () {
            apiclient.getFunctionsByCinemaAndDate(_cinema, _date, _mapOneByOne);
        });
    }

    function borrar() {
        clearCanvas();
        $.getScript(_module, function () {
            apiclient.deleteFunction(_cinema, fun, _actualizar);
        });
    }

    function crear() {
        var seats = [[true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true]];
        var movieName = $("#movieName").val();
        var movieGenre = $("#movieGenre").val();
        var date = _date + " " + $("#functionHour").val();
        var f = {"movie": {"name": movieName, "genre": movieGenre}, "seats": seats, "date": date};
        $.getScript(_module, function () {
            apiclient.createFunction(_cinema, f, _actualizar);
        });
        $('#create').modal('hide');
    }

    return {
        getFunctions: getFunctions,
        openSeats: openSeats,
        update: update,
        borrar: borrar,
        crear: crear,
        getMousePosition: getMousePosition

    };

})();
        