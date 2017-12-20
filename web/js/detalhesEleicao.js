$(document).ready( function() {
        var smv = function () {
            var name = $("#reg_eleicao").val();
            $.ajax({
                type: "POST",
                url: "getDetalhes?eleicao=" + name,
                dataType: "text",
                success: function (data1) {
                    var html1 = "" + data1;
                    $("#resultados").html(html1);
                },
                error: function (data) {
                    console.log("erro a pedir detalhes!");
                }
            });

        };

       $("#pesquisa").click(smv);

    }
);