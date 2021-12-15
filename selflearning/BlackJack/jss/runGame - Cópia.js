
let deck = [];
let count=0;
let somePlayer=0;
let someDealer=0;
let wins=0;
let loses=0;
let draws=0;
let wallet=100;
let playerBet;
createDeck(deck);
deck = shuffleCards(deck);
//create and shuffleCards

function createDeck(deck) {
    createNipe('spades');
    createNipe('diamons');
    createNipe('hearts');
    createNipe('clubs');
    
   }
function createNipe(nipe){
    
    for (let i = 0; i < 13; i++) {
       
        if(i<=12){
            
            if(i===0){
                deck.push({name:'ace'+nipe,
                points:11,
                linkImg:'ace'+nipe+'.png'});

            }else if(i>=1&&i<=9){
                deck.push({name:(i+1)+nipe,
                    points:i+1,
                    linkImg:i+1+nipe+'.png'})

            }else if(i===10){
                deck.push({name:'queen'+nipe,
                points:10,
                linkImg:'queen'+nipe+'.png'})

            }else if(i===11){
                deck.push({name:'jack'+nipe,
                points:10,
                linkImg:'jack'+nipe+'.png'})

            }else if(i===12){
                deck.push({name:'king'+nipe,
                points:10,
                linkImg:'king'+nipe+'.png'})

            }
    }

}
}
function shuffleCards(deck = []) {
    for (let i = 0; i < 100; i++) {
      deck.splice(Math.floor(Math.random() * 60), 0, deck.splice(Math.floor(Math.random() * 52), 1)[0]);
   
    }
    return deck;
   }

//buttons functions   

function hit(){
    playerBet=document.getElementById('bet').value
    if(count===0){
        for (let i = 0; i < 2; i++) {
                //pull card Img on Table
        cardOnTable('playerDivCard');
        
        //some points when player hit 
        somePlayer=somePoints(somePlayer);
    
        document.getElementById('playerPoints').innerHTML=somePlayer;
        if(somePlayer>21){
            document.getElementById('blackJackResult').innerHTML='You Lose';
            wallet=cashOnwellet('lose',playerBet);//change 10 to bet , bet most be the player choice
            loses++
        }
        
        count++;
            
        }}else{
        if (document.getElementById('blackJackResult').innerHTML!='Let´s Play' ){
            deal();
    
        }else{
        //pull card Img on Table
        cardOnTable('playerDivCard');
        
        //some points when player hit 
        somePlayer=somePoints(somePlayer);
    
        document.getElementById('playerPoints').innerHTML=somePlayer;
        if(somePlayer>21){
            document.getElementById('blackJackResult').innerHTML='You Lose'
            wallet=cashOnwellet('lose',playerBet);//change 10 to bet , bet most be the player choice
            loses++
        }
        
        count++;
        }
    }


}

function cardOnTable(div){
    let cardImg
    console.log(deck[count])
    cardImg=document.createElement('img');
    cardImg.setAttribute('src',deck[count].linkImg);
    cardImg.setAttribute('class','cardImg');
    document.getElementById(div).appendChild(cardImg);
}


function somePoints(total){
   
total=total+deck[count].points;
return total

}

function deal(){//reset the game and set results in table
    deck = shuffleCards(deck);
    somePlayer=0;
    someDealer=0;
    count=0;
    document.getElementById('playerDivCard').innerHTML='';
    document.getElementById('playerPoints').innerHTML=0;
    document.getElementById('dealerDivCard').innerHTML='';
    document.getElementById('delaerPoints').innerHTML=0;
    document.getElementById('blackJackResult').innerHTML='Let´s Play';
    document.getElementById('winTd').innerHTML=wins;
    document.getElementById('loseTd').innerHTML=loses;
    document.getElementById('drawTd').innerHTML=draws;

}

function stand(){
    
    if (document.getElementById('blackJackResult').innerHTML!='Let´s Play'){
        deal();

    }else{
    while (someDealer<17) {
        someDealer=somePoints(someDealer);
        cardOnTable('dealerDivCard');
        document.getElementById('delaerPoints').innerHTML=someDealer;
        count++;
     if(someDealer>21){
        document.getElementById('blackJackResult').innerHTML='You Win';
        wallet=cashOnwellet('win',playerBet);//change 10 to bet , bet most be the player choice
        wins++
        return;
         }

    }
    
    if(somePlayer>someDealer){
        document.getElementById('blackJackResult').innerHTML='You Win';
        wallet=cashOnwellet('win',playerBet);//change 10 to bet , bet most be the player choice
        wins++;
    }else if(someDealer>somePlayer) {
        document.getElementById('blackJackResult').innerHTML='You Lose';
        wallet=cashOnwellet('lose',playerBet);//change 10 to bet , bet most be the player choice
        loses++;
    }else if(someDealer===somePlayer){
        document.getElementById('blackJackResult').innerHTML='Draw';
        wallet=cashOnwellet('draw',playerBet);//change 10 to bet , bet most be the player choice
        draws++;
    }  }

    }
function cashOnwellet(result,bet) {
    let finalResult
    if (result==='win'){
        finalResult=wallet+(bet*2);
        
    }else if(result==='lose'){
        
        finalResult=wallet-bet;
        console.log(finalResult);

    }else if(result==='draw'){
        finalResult=wallet;

    }
    document.getElementById('cash').innerHTML=finalResult;
    return finalResult
}





