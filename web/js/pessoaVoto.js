$(document).ready( function() {

    if(!admin){
        $("#reg_cc1").val(user);
        $("#reg_cc1").hide();
    }
    var smv = function () {
        console.log("ajax!");
        var cc = (parseInt($("#reg_cc1").val()));
        var name = $("#reg_nome1").val();
        console.log("----"+cc);
        $.ajax({
            type: "POST",
            url: "getPessoaVoto",
            data: {cc: cc, eleicao: name},
            dataType: "text",
            success: function (data1) {
                var html1 = "" + data1;
                $("#resultados").html(html1);
            },
            error: function (data) {
                console.log("erro a pedir pessoaVoto!");
            }
        });

    }

    $("#pesquisa").click(function(){
        //console.log($("#reg_cc1").val());
        //console.log($("#reg_nome1").val());
        smv();
    });

}
);

