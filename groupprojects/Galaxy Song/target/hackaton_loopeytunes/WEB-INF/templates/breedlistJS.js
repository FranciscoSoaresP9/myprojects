$(document).ready(function () {

    $.ajax({
        url: "https://api.TheDogAPI.com/v1/breeds",
        async: true,
        success: successCallback,
        error: errorCallback
    });

    $.ajax({
        url: "http://localhost:8080/hackathon/api/senddog",
        async: true,
        success: getDogForm,
        error: dogFormError
    });
    var dogForm;

    function dogFormError() {

    }

    function getDogForm(response) {
        dogForm = response;
    }

    function successCallback(response) {

        splitTemperement(response);
        heightConverter(response);
        replaceLifespan(response);

        var reduced = reduceByHeight(response, dogForm.height);

        var reduced2 = reduceByEnergy(reduced, dogForm.energy);

        var reduced3 = reduceByPurpose(reduced2, parseInt(dogForm.purpose));

        var reduced4 = reduceByLifespan(reduced3, dogForm.lifespan);


        if(reduced4.length === 0) {

            buildListPage(getCat());
            }else {

            buildListPage(reduced4);
        }



    }

    function getCat(){
        let url = "https://www.rd.com/wp-content/uploads/2019/09/Cute-cat-lying-on-his-back-on-the-carpet.-Breed-British-mackerel-with-yellow-eyes-and-a-bushy-mustache.-Close-up-e1573490045672.jpg";
        let name = "cat";
        return cat = [{
            image: {url},
            name: name
        }]
    }

    function splitHeight(dog) {
        if (dog.height.metric.length === 2) {
            return dog.height.metric;
        } else {
            return dog.height.metric.split(" - ")[1];
        }
    }

    function heightConverter(response) {
        response.forEach((dog) => dog.height.metric = parseInt(splitHeight(dog)));
    }

    function reduceByHeight(response, viewValue) {
        var dogsFilter;
        switch (viewValue) {
            case 1 :
                dogsFilter = response.filter((dog) => dog.height.metric < 50);
                break;

            case 2:
                dogsFilter = response.filter((dog) => dog.height.metric >= 50);
                break;

        }
        return dogsFilter;

    }

    function errorCallback(request, status, error) {

    }

    function splitTemperement(response) {
        return response.forEach((dog) => {
            if (dog.temperament === undefined) {
                dog.temperament = "Energetic,Alert,Trainable,Active";
            }
            dog.temperament = dog.temperament.split(",");
        });
    }

    function reduceByEnergy(response, viewValue) {
        var arrDogs = [];
        switch (viewValue) {
            case 1:
                response.filter((dog) => {
                    if ((searchElements("Steady", dog.temperament)).length >= 1) {
                        arrDogs.push(dog);
                    } else if ((searchElements("Sweet-Tempered", dog.temperament)).length >= 1)
                        arrDogs.push(dog);

                    else if ((searchElements("Even Tempered", dog.temperament)).length >= 1) {
                        arrDogs.push(dog);
                    } else if ((searchElements("Calm", dog.temperament)).length >= 1) {
                        arrDogs.push(dog);
                    }
                });
                return arrDogs;
            case 2:
                response.filter((dog) => {
                    if ((searchElements("Steady", dog.temperament)).length === 0) {
                        if ((searchElements("Sweet-Tempered", dog.temperament).length === 0)) {
                            if ((searchElements("Even Tempered", dog.temperament).length === 0))
                                if ((searchElements("Energetic", dog.temperament).length === 0))
                                    arrDogs.push(dog);
                        }
                    }
                });
                return arrDogs;
            case 3:
                response.filter((dog) => {
                    if ((searchElements("Energetic", dog.temperament)).length >= 1) {
                        arrDogs.push(dog);
                    }
                });
                return arrDogs;
        }
    }

    function searchElements(string, where) {
        return where.filter(s => s.includes(string));
    }

    function reduceByPurpose(response, viewValue) {
        var arrDogs = [];
        switch (viewValue) {

            case 1 :
                response.forEach((dog) => {
                    if ((searchElements("Friendly", dog.temperament)).length >= 1) {
                        arrDogs.push(dog);
                    } else if ((searchElements("Loyale", dog.temperament)).length >= 1) {
                        arrDogs.push(dog);
                    } else if ((searchElements("Companionable", dog.temperament)).length >= 1) {
                        arrDogs.push(dog);
                    }
                });
                break;
            case 2 :
                response.forEach((dog) => {
                    if ((searchElements("Proactive", dog.temperament)).length >= 1) {
                        arrDogs.push(dog);
                    } else if ((searchElements("Alert", dog.temperament)).length >= 1) {
                        arrDogs.push(dog);
                    } else if ((searchElements("Territorial", dog.temperament)).length >= 1) {
                        arrDogs.push(dog);
                    }
                });
                break;
            case 3:
                response.forEach((dog) => {
                    if ((searchElements("Familial", dog.temperament)).length >= 1) {
                        arrDogs.push(dog);
                    } else if ((searchElements("Loyal", dog.temperament)).length >= 1) {
                        arrDogs.push(dog);
                    }
                });
                break;
        }
        ;
        return arrDogs;
    }

    function reduceByLifespan(response, viewValue) {
        var arrDogs = [];
        switch (viewValue) {
            case 1 :
                arrDogs = response.filter((dog) => dog.life_span <= 13);
                break;
            case 2 :
                arrDogs = response.filter((dog) => dog.life_span > 13);
                break;
        }
        return arrDogs;

    }

    function replaceLifespan(response) {
        response.forEach((dog) => {
            if (!(dog.life_span.includes("-"))) {
                dog.life_span = parseInt(dog.life_span.split(" ")[0]);
            } else {
                dog.life_span = parseInt(dog.life_span.split(" - ")[1].split(" ")[0]);
            }
        })
    }

    function buildListPage(filteredData){

        let div;
        filteredData.forEach((animal)=>{
             div="<div id="+animal.name+"><img class='image' src="+animal.image.url+">" +
                 "<form id='"+animal.id+"' class='breed_info' action='doglist/" + animal.id + "' method='GET'>" +
                     "<input type='submit' value='breed view'>" +
                 "</form>" +
                 "</div>";



           // img= '<img src='+animal.image.url+'>'

             $(div).appendTo($('#image_frame'));
            }

        )
    }
})
;
