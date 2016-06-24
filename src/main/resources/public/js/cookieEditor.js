/**
 * Created by aldm on 10.06.2016.
 */
$(document).ready(function() {
    $('#login').on('click', function () {
        var login = $('#username').val();
        var password = $('#password').val();
        $.cookie('login', login);
        $.cookie('password', password);
    });

    $('#logout').on('click', function () {
        $.cookie('login', null);
        $.cookie('password', null);
    });
});
