/**
 * Created by Alexander on 18.08.2015.
 */
var input;

/*---------------- Отвечает за автозаполнение ------------------------*/
$(document).ready(function () {
    $("#input").keyup(function () {
        var numChars = $(this).val().length;
        func();
    });
});

/*---------------- Посылает заппрос к PopUpMenusHandler ------------------------*/
function func() {
    input = $("#input").val();
    $.get("/menuHandler", {inputfield: input}, function (data) {
        if (data == "") {
            $("#results").hide();
        }
        else {
            $("#results").show();
            $("#results").html(data);
        }
    });
}

/*---------------- Подставляет в INPUT выбранную строку ------------------------*/
$(document).on('click','li',function(){
    var avtor = $(this).text(); // получаем значение со строки "td"
    $("#input").val(avtor);
});

/*---------------- Скрываем варианты автодополнения после выбора строки ------------------------*/
$(document).on('click', 'li', function() {
    $("li").hide();
});

/*---------------- Отвечают за окно с добавлением новых книг ------------------------*/
function deselect(e) {
    $('.pop').slideFadeToggle(function() {
        e.removeClass('selected');
    });
}

/* Открытия окна бобавления новых книг при переходе на страницу */
/*
$(document).ready(function() {
    if ($('#contact').hasClass('selected')) {
        deselect($('contact'));
    } else {
        $('#contact').addClass('selected');
        $('.pop').slideFadeToggle();
    }
});
*/
/*---------------------------------------------------------------*/


$(function() {
    $('#contact').on('click', function() {
        if($(this).hasClass('selected')) {
            deselect($(this));
        } else {
            $(this).addClass('selected');
            $('.pop').slideFadeToggle();
        }
        return false;
    });

    $('.close').on('click', function() {
        deselect($('#contact'));
        return false;
    });
});

$.fn.slideFadeToggle = function(easing, callback) {
    return this.animate({ opacity: 'toggle', height: 'toggle' }, 'fast', easing, callback);
};
/*---------------------------------------------------------------------------------------*/