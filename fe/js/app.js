 (function(window) {
     'use strict';

     var liHteml = "<li id=";
     var viewHtml = "><div class='view'><input class='toggle' type='checkbox'>";
     var todoLabelHtml = "</label><button class='destroy'></button></div><li>";
     var labelHteml = "<label>";
     var completedHtml = "<li class='completed' id=";

     var ALLVIEW = 2;
     var ACTIVEVIEW = 0;
     var COMPLETEDVIEW = 1;

     $(document).ready(function() {
         fillView(ALLVIEW);
     });

     function fillView(optionId) {
         $('.todo-list').empty();
         $.ajax({
             url: "./api/todos",
             method: 'GET',
             dataType: 'json',
             success: function(response) {
                 var tag;
                 for (var i = 0; i < response.length; i++) {
                     var todoStr = response[i];
                     if (optionId == ACTIVEVIEW) {
                         if (todoStr.completed == 0) {
                             tag = liHteml + todoStr.id + viewHtml;
                             tag = tag + labelHteml + todoStr.todo + todoLabelHtml;
                         }
                     } else if (optionId == COMPLETEDVIEW) {
                         if (todoStr.completed == 1) {
                             tag = completedHtml + todoStr.id + viewHtml;
                             tag = tag + labelHteml + todoStr.todo + todoLabelHtml;
                         }
                     } else {
                         if (todoStr.completed) {
                             tag = completedHtml + todoStr.id + viewHtml;
                             tag = tag + labelHteml + todoStr.todo + todoLabelHtml;
                         } else {
                             tag = liHteml + todoStr.id + viewHtml;
                             tag = tag + labelHteml + todoStr.todo + todoLabelHtml;
                         }
                     }
                     $('.todo-list').append(tag);
                     tag = "";
                 }
                 getCountItem();
             }
         })
     }

     function changeSelected(option) {
         $('.filters').find('li').find('a').removeClass();
         $(option).addClass('selected');
     }

     function getCountItem() {
         $.ajax({
             url: './api/todos',
             method: 'get',
             dataType: 'json',
             success: function(response) {
                 $('.todo-count').find('strong').text(response.length);
             }
         })
     }

     function insert() {
         var todoVal = $('.new-todo').val();
         $('.new-todo').val("");
         $.ajax({
             url: './api/todos/',
             method: 'POST',
             headers: { 'Content-Type': 'application/json' },
             dataType: 'json',
             data: JSON.stringify({ todo: todoVal, completed: 0 }),
             success: function(response) {
                 if ($('#completed').hasClass('selected') == false) {
                     var tag = liHteml + response.id + viewHtml;
                     tag = tag + labelHteml + response.todo + todoLabelHtml;
                     $('.todo-list').prepend(tag);
                 }
                 getCountItem();
             }
         })
     }

     function deleteTodo(option) {
         var request = "";
         var component = "#" + option;

         if (option >= 0) {
             request = "/" + option;
         }

         $.ajax({
             url: './api/todos' + request,
             method: 'DELETE',
             headers: { 'Content-Type': 'application/json' },
             dataType: 'json',
             success: function(response) {
                 if (option >= 0) {
                     $(component).remove();
                 } else {
                     $('.completed').remove();
                 }
                 getCountItem();
             }
         })
     }

     function updateTodo(id, check) {
         var completed;
         if (check) {
             completed = 1;
         } else {
             completed = 0;
         }

         $.ajax({
             url: './api/todos/' + id,
             method: 'PUT',
             headers: { 'Content-Type': 'application/json' },
             dataType: 'json',
             data: JSON.stringify({ completed: completed }),
             success: function() {
                 getCountItem();
             }
         })
     }

     $('#active').click(function() {
         changeSelected('#active');
         fillView(ACTIVEVIEW);
     });

     $('#completed').click(function() {
         changeSelected('#completed');
         fillView(COMPLETEDVIEW);
     });

     $('#all').click(function() {
         changeSelected('#all');
         fillView(ALLVIEW);
     });

     $('.new-todo').keypress(function(e) {
         if (e.which == 13 && $('.new-todo').val() != "") {
             insert();
         }
     });

     $('.todo-list').on('click', '.destroy', function() {
         var id = $(this).parents('li').attr('id');
         deleteTodo(id);
     });

     $('.clear-completed').click(function() {
         $('.todo-list').find('.completed').each(function(i, e) {
             var id = $(this).attr('id');
             deleteTodo(id);
         });
     });

     $('.todo-list').on('click', '.toggle', function() {
         var id = $(this).parents('li').attr('id');
         var check = this.checked;
         if (check) {
             $(this).parents('li').addClass('completed');
         } else {
             $(this).parents('li').removeClass('completed');
         }
         updateTodo(id, check);
     })

 })(window);