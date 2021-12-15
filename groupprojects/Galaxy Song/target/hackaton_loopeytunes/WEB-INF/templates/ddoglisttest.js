$(document).ready(function () {
    var url = window.location.pathname;
    var arr = url.split("/");
    var id=arr[3];
    $.ajax({
        url: "https://api.TheDogAPI.com/v1/breeds",
        async: true,
        success: successCallback,
        error: errorCallback
    });

    function successCallback(response) {
        console.log(response)
        console.log(id);
        dogsFilter = response.filter((dog) =>  dog.id === parseInt(id));
buildPage(dogsFilter[0]);
    }

    function errorCallback() {
    }

function buildPage(dogsFilter){
        if(dogsFilter.origin===undefined){
            dogsFilter.origin="Unknown";
        }
      let element="<div class='text'><h1>"+dogsFilter.name+"</h1>"+
   " <h3>Height</h3><p>"+dogsFilter.height.metric+"</p>"+
   " <h3>Life_span</h3>"+dogsFilter.life_span+"<p></p>"+
   " <h3>Temperament</h3><p>"+dogsFilter.temperament+"</p>"+
    "  <h3>Origin</h3><p>"+dogsFilter.origin+"</p></div>" +
        " <div class='image'> <img class='square' src="+dogsFilter.image.url+"> </div> "
    $(element).appendTo('body')
}


})