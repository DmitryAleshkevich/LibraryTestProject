/**
 * Created by aldm on 10.06.2016.
 */
function getMyBooks() {
    $.ajax({
        type: "GET",
        url: "/getrentedbooks",
        data: "login=" + $.cookie('login') + "&password=" + $.cookie('password'),
        success: function (responce) {
            if (responce.size != 0) {
                $('#mybooks').empty();
                $('#mybooks').append('<table id="rentedBooks" class="table"><thead><tr><th>Include</th><th>Id</th><th>Title</th><th>Authors</th><th>Release Date</th></tr></thead></table>');
                $.each(responce, function (index, value) {
                    var row = $('<tr><td class="vcenter"><input type="checkbox" id="flag"/></td><td>' + value.id + "</td><td>" + value.title + "</td><td>" + value.authors + "</td><td>" + value.releaseYear + "</td></tr>");
                    $('#rentedBooks').append(row);
                });
                var containerTake = $('<div class="input-group col-md-3" id="divPush"><span class="input-group-btn"><button type="button" class="btn btn-primary" id="pushBooks">Push books</button></span></div><script type="text/javascript">bindNewPushButton();</script>');
                $('#mybooks').append(containerTake);
            }
        }
    })
}

function pickDate() {
    $(function () {
        $("#pickedDate").datetimepicker({pickTime: false});
    });
}

function bindNewPushButton() {
    $('#pushBooks').on('click',function () {
        var books = collectBooks('#rentedBooks');
        if (books.length > 0) {
            $.ajax({
                type: "POST",
                url: "/pushbooks",
                contentType: 'application/json',
                data: JSON.stringify(books),
                dataType: 'json',
                processData: false,
                success: function (responce) {
                    if (responce.success) {
                        location.reload();
                    };
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
        }
    })
}

function collectBooks(container) {
    var books = [];
    $(container).find('tr').each(function () {
        var book = {};
        var include = false;
        $(this).find('td').each(function () {
            var key = this.offsetParent.tHead.childNodes[0].cells[this.cellIndex].innerText;
            var value = this.innerText;
            if (key != "Include") {
                var newKey;
                switch (key) {
                    case "Id":
                    {
                        newKey = "id";
                        break;
                    }
                    case "Title":
                    {
                        newKey = "title";
                        break;
                    }
                    case "Authors":
                    {
                        newKey = "authors";
                        break;
                    }
                    case "Release Date":
                    {
                        newKey = "releaseYear";
                        break;
                    }
                }
                book[newKey] = value;
            }
            $(this).find('input').each(function () {
                include = this.checked;
            });
        });
        if (include) {
            books[books.length] = book;
        }
    });
    return books;
}
function bindNewButton() {
    $('#takeBook').on('click', function () {
        var date = $('#pickedDate').val();
        if (date == "") {
            alert("Date is empty!");
            return 0;
        }
        var books = collectBooks('#searchResults');
        if (books.length > 0) {
            var query = {};
            query['books'] = books;
            query['date'] = new Date(date);
            query['login'] = $.cookie('login');
            query['password'] = $.cookie('password');
            $.ajax({
                type: "POST",
                url: "/rentbooks",
                contentType: 'application/json',
                data: JSON.stringify(query),
                dataType: 'json',
                processData: false,
                success: function (responce) {
                    if (responce.success) {
                        location.reload();
                    };
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
        }
    });
}

$(document).ready(function () {
    $('#searchBook').on('click', function () {
        var author = $('#author').val();
        var book = $('#book').val();
        var request = {};
        request[book] = author;
        $.ajax({
            type: "POST",
            url: "/getbooks",
            contentType: 'application/json',
            data: JSON.stringify(request),
            dataType: 'json',
            processData: false,
            success: function (responce) {
                if (responce.size != 0) {
                    $('#searchContent').empty();
                    $('#searchContent').append('<table id="searchResults" class="table"><thead><tr><th>Include</th><th>Id</th><th>Title</th><th>Authors</th><th>Release Date</th></tr></thead></table>');
                    $.each(responce, function (index, value) {
                        var row = $('<tr><td class="vcenter"><input type="checkbox" id="flag"/></td><td>' + value.id + "</td><td>" + value.title + "</td><td>" + value.authors + "</td><td>" + value.releaseYear + "</td></tr>");
                        $("#searchResults").append(row);
                    });
                    var containerTake = $('<div class="input-group col-md-3" id="withDate"><span class="input-group-addon" id="basic-addon1">Desired date:</span><input type="date" class="form-control" id="pickedDate" aria-describedby="basic-addon1"><span class="input-group-btn"><button type="button" class="btn btn-primary" id="takeBook">Take books</button></span></div><script type="text/javascript">pickDate();bindNewButton();</script>');
                    $('#searchContent').append(containerTake);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            },
            complete: function (jqXHR, textStatus) {
                console.log(textStatus);
            }
        })
    });
});
