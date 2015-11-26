var book;
var shelf;

$(document).ready(function () {
   $('#edit').hide();
});

$(document).on('click', "#a", function () {
    shelf = $(this).text().slice(8);
});

$(document).on('click', '#book', function () {
    book = this;
    var author = $('#author', this).text().slice(7);
    var bookname = $('#bookname', this).text().slice(12);
    var id = $('#id_b', this).text();
    var isDeleted = $('#isDeleted', this).text();

    $('#edit #bookauthor').val(author);
    $('#edit #bookname').val(bookname);
    $('#edit #id').val(id);
    $('#edit #isDeleted').val(isDeleted);
    $('#edit #shelf').val(shelf);
    $('#edit #newShelfDiv').hide();
    $('#edit #idDiv').hide();
    $('#edit #isDeletedDiv').hide();
    $('#edit #shelfDiv').hide();

    if (isDeleted == "1") {
        $('#edit #new_message #delete').text("Вернуть");
    }

    $('#edit').fadeTo(700, 0.99);
    $('#edit').draggable();
});

$(document).ready(function () {
   $('.hidden').hide();
});

$(document).on('click', '#editCancelBtn', function (){
   $('#edit').fadeOut(500);
});


$(document).on('click', '#delete', function () {
    var deleteButton = this;
    var id_b = $('#edit #new_message #id').val();
    var isDeleted = $('#edit #new_message #isDeleted').val();

    $.ajax({
        url: "AddRemoveModify",
        data: {id: id_b, action: "delete", isDeleted: isDeleted}
    }).done(function () {
        if (isDeleted == "1") {
            $(book).fadeTo(500, 0.99);
            $(deleteButton).text("Взять");
            $('#isDeleted', book).text("0");
            $('#edit #new_message #isDeleted').val("0");
        } else {
            $(book).fadeTo(500, 0.3);
            $(deleteButton).text("Вернуть");
            $('#isDeleted', book).text("1");
            $('#edit #new_message #isDeleted').val("1");
        }
    });
});



$(document).on('click', '#modify', function () {
    $('#edit #newShelfDiv').show();
});

$(document).on('click', '#enter', function () {
    var newShelf = $('#edit #newShelf').val();
    var id = $('#edit #new_message #id').val();

    if (newShelf != "") {
        $.ajax({
            url: "AddRemoveModify",
            data: {id: id, action: "modify", newShelf: newShelf}
        }).done(function () {
            $('#edit').hide(500);
            $(book).slideToggle(500);
            setTimeout(function () {
                alert("Книга успешно перемещена.")
            }, 700);
        });
    }
});


