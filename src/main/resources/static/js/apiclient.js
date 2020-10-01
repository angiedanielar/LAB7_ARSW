apiclient = (function () {


    function getFunctionsByCinema(cinema_name, callback) {
        //http://localhost:8080/cinema/{name}
        $.getJSON("/cinema/" + cinema_name, function (data) {
            callback(data);
        });
    }

    function getFunctionsByCinemaAndDate(cinema_name, fdate, callback) {
        //http://localhost:8080/cinema/{name}/{date}
        $.getJSON("/cinema/" + cinema_name + "/" + fdate, function (data) {
            callback(data);
        });

    }

    function getFunctionByFunctionNameAndDate(cinema_name, fdate, movie_name, callback) {
        //http://localhost:8080/cinema//{name}/{date}/{moviename}
        $.getJSON("/cinema/" + cinema_name + "/" + fdate + "/" + movie_name, function (data) {
            console.log(data);
            callback(data);
        });
    }

    function saveUpdateFunction(cinema_name, cinema_function, callback) {

        var cinemaFunction = JSON.stringify(cinema_function);
        console.log(cinemaFunction);
        const promise = $.ajax({
                url: "/cinema/" + cinema_name,
                type: 'PUT',
                data: JSON.stringify(cinema_function),
                contentType: "application/json"
            }).done(function () {
                resolve('Function hour updated');
            }).fail(function (msg) {
                reject('The hour was not specified');
            });

        promise
                .then(res => {                    
                    callback();            
                })
                .catch(error => {
                    alert(error);
                });


    }

    function createFunction(cinema_name, f, callback) {
        var cinemaFunction = JSON.stringify(f);
        const promise = new Promise((resolve, reject) => {
            $.ajax({
                url: "/cinema/" + cinema_name,
                type: 'PUT',
                data: cinemaFunction,
                contentType: "application/json"
            }).done(function () {
                resolve('SUCCESS');
            }).fail(function (msg) {
                reject('FAIL');
            });
        });

        promise
                .then(res => {
                    callback();
                })
                .catch(error => {
                    alert(error);
                });
    }

    function deleteFunction(cinema_name, f, callback) {
        var cinemaFunction = JSON.stringify(f);
        const promise = new Promise((resolve, reject) => {
            $.ajax({
                url: "/cinema/" + cinema_name,
                type: 'DELETE',
                data: cinemaFunction,
                contentType: "application/json"
            }).done(function () {
                resolve('Function deleted');
            }).fail(function (msg) {
                reject('No function was selected');
            });
        });

        promise
            .then(res => {
                callback();
            })
            .catch(error => {
                alert(error);
            });
    }

    return {
        getFunctionsByCinema: getFunctionsByCinema,
        getFunctionsByCinemaAndDate: getFunctionsByCinemaAndDate,
        getFunctionByFunctionNameAndDate: getFunctionByFunctionNameAndDate,
        saveUpdateFunction: saveUpdateFunction,        
        createFunction: createFunction,
        deleteFunction: deleteFunction

    }

})();