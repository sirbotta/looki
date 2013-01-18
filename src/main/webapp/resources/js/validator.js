/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$('#addPr').validate({		
    rules: {
            product: 'required',
            qnt:{
                required: true,
                number: true 
            },
            um: 'required',
            price:{
                required: true,
                number: true 
            }
        },

    messages: {
                product: "<span> Immettere il nome del prodotto</span>",
                qnt:{
                    required: "<span> Immettere la quantit&agrave; del prodotto</span><br>",
                    number: "<span> Immettere un numero per la quantità di prodotto da inserire</span><br>" 
                },
                um: "<span> Immettere l'unit&agrave; di misura della quantit&agrave; del prodotto</span>",
                price:{
                required: "<span> Immettere il prezzo del prodotto</span>",
                number: "<span> Immettere un numero per indicare il prezzo del prodotto</span>"
            }
    }

});


$("input").keypress(function (evt) {
//Deterime where our character code is coming from within the event
var charCode = evt.charCode || evt.keyCode;
if (charCode  == 13) { //Enter key's keycode
return false;
}
});