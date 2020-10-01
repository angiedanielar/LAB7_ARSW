
apimock = (function () {

    var seats = [[true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true]];
    var mockdata = [];
    var function1X = {"movie": {"name": "The Enigma", "genre": "Drama"}, "seats": seats, "date": "2018-12-18 15:30"};
    var function2X = {"movie": {"name": "The Enigma 2", "genre": "Drama"}, "seats": seats, "date": "2018-12-18 15:30"};
    var function1Y = {"movie": {"name": "SuperHeroes Movie", "genre": "Action"}, "seats": seats, "date": "2018-12-19 17:00"};
    var function2Y = {"movie": {"name": "The Night", "genre": "Horror"}, "seats": seats, "date": "2018-12-19 19:40"};
    var function3Y = {"movie": {"name": "SuperHeroes Movie", "genre": "Action"}, "seats": seats, "date": "2018-12-19 14:30"};
    var function4Y = {"movie": {"name": "The Enigma", "genre": "Drama"}, "seats": seats, "date": "2018-12-20 17:00"};
    var function5Y = {"movie": {"name": "Coraline", "genre": "Horror"}, "seats": seats, "date": "2018-12-20 17:00"};
    var function6Y = {"movie": {"name": "Shrek", "genre": "Comedy"}, "seats": seats, "date": "2018-12-20 17:00"};
    var function7Y = {"movie": {"name": "Shrek 2", "genre": "Comedy"}, "seats": seats, "date": "2018-12-20 17:00"};
    var function8Y = {"movie": {"name": "Inception", "genre": "Drama"}, "seats": seats, "date": "2018-12-20 17:00"};

    mockdata["cinemaX"] = {"name": "cinemaX", "functions": [function1X, function2X]};
    mockdata["cinemaY"] = {"name": "cinemaY", "functions": [function1Y, function2Y, function3Y, function4Y]};
    mockdata["cinemaZ"] = {"name": "cinemaZ", "functions": [function5Y, function6Y]};
    mockdata["cinemaW"] = {"name": "cinemaW", "functions": [function7Y, function8Y]};

    return {
        getFunctionsByCinema: function (cinema_name, callback) {
            callback(mockdata[cinema_name]);
        },
        getFunctionsByCinemaAndDate: function (cinema_name, fdate, callback) {
            callback(
                    mockdata[cinema_name].functions.filter(
                    funct => funct.date.includes(fdate))
                    );
        },
        getFunctionByFunctionNameAndDate: function (cinema_name, fdate, movie_name, callback) {
            var data = mockdata[cinema_name].functions.filter(
                    funct => funct.date.includes(fdate));
            var funcion = data.find(element => element.movie.name == movie_name);
            callback(funcion);
        }

    }

})();







/*
 Example of use:
 var fun=function(list){
 console.log(list);
 }
 apimock.getFunctionsByCinema("cinemaX",fun);
 apimock.getFunctionsByCinemaAndDate("cinemaX","2018-12-18",fun);*/
