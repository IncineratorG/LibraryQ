/**
 * Created by Alexander on 18.08.2015.
 */
var input;

/*---------------- Отвечает за автозаполнение ------------------------*/
$(document).ready(function () {
    $("#input").keyup(function () {
       $('#output').fadeTo(1000, 0.2);
        var numChars = $(this).val().length;
        func();
    });
});

/*---------------- Посылает заппрос к PopUpMenusHandler ------------------------*/
function func() {
    input = $("#input").val();
    $.get("menuHandler", {inputfield: input}, function (data) {
        if (data == "") {
            $("#results").hide();
        }
        else {
            $("#results").show();
            $("#results").html(data);
        }
    });
}

/*---------------- Затемнение окна с результатами при вводе нового запроса ------------------------*/
$(document).on('click', '#output', function() {
   $('#output').fadeTo(1000, 0.95);
});

/*---------------- Подставляет в INPUT выбранную строку ------------------------*/
$(document).on('click','li',function(){
    var avtor = $(this).text(); // получаем значение со строки "td"
    $("#input").val(avtor);
    alert
});

/*---------------- Скрываем варианты автодополнения после выбора строки ------------------------*/
$(document).on('click', 'li', function() {
    $("li").hide();
});

/*---------------- Развернуть окно с результатами ------------------------*/
$(document).ready(function() {
   $('#authorOnShelf .hide').hide();
    $('#authorOnShelf #a').on('click', function() {
        $('#authorOnShelf .hide').slideUp(300);
        $(this).parent().find(".hide").slideDown(300);
    });
});

/*---------------- Сдвиг влево окна с результатами (Полка №..) ------------------------*/
$(document).on('click', '#a', function() {
   $('#output').animate({
       "left": "50px"
   }, 1000);
});


/*---------------- Отвечают за окно с добавлением новых книг ------------------------*/
function deselect(e) {
    $('.pop').slideFadeToggle(function() {
        e.removeClass('selected');
    });
}

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


/*---------------- Отвечает за подсвечивание выбранной полки ------------------------*/
$(document).on('click', '#a', function () {
		var shelfString = $(this).text().slice(8);
		var shelfNumber = parseInt(shelfString);

		if (shelfNumber >= 1 && shelfNumber < 5) {
			$('#bgImage').css('background-image', 'url(shelfs/1.jpg)');
		} else if (shelfNumber >= 5 && shelfNumber < 16) {
				$('#bgImage').css('background-image', 'url(shelfs/2.jpg)');
			}	else if (shelfNumber >= 16 && shelfNumber < 22) {
					$('#bgImage').css('background-image', 'url(shelfs/3.jpg)');
				}	else if (shelfNumber >= 22 && shelfNumber < 34) {
						$('#bgImage').css('background-image', 'url(shelfs/4.jpg)');
					}	else if (shelfNumber >= 34 && shelfNumber < 38) {
							$('#bgImage').css('background-image', 'url(shelfs/5.jpg)');
						}	else if (shelfNumber >= 38 && shelfNumber < 40) {
								$('#bgImage').css('background-image', 'url(shelfs/6.jpg)');
							}
		
		var pointerPosition = "_" + shelfString;
        //alert(pointerPosition);

        $('#rectangle').attr('class', pointerPosition);
		//$('#rectangle').removeClass();
		//$('#rectangle').addClass(pointerPosition);
});	
/*---------------------------------------------------------------------------------------*/