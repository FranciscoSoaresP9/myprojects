var engine = require('workshop-engine');

var forcePowerObtain
var vulnerabilitiesObtain

var info={all:function(){

},satageType1:function(obtainName,valueObtain,finalValue){
    console.log(obtainName+' obtain: '+valueObtain);
    console.log('Total '+obtainName+' : '+finalValue);
  },
  satageType2:function(obtainName,valueObtain,finalValue){
    console.log(obtainName+' lose: '+valueObtain);
    console.log('Total '+obtainName+': '+finalValue);

  }
}

//-------------------------MERCHANT-------------------------------------
var merchantQuestions = ['What is it, what is it? Made to walk and not walk.',
  "What is it, what is it? You don't eat, but it's good to eat.",
  "What is it, what is it? The more you take, the more you increase.",
  "350+193",
  'What is the birth name of Dark Vader',
  'On which planet Anakin fight Obi Wan Kenobi?',
  'What is the color of the Sith saber?',
  'What is the name of the droid that always appears in every movie?',
  'What is more useful when it is broken?',
  'What belongs to you, but other people use it more than you?',
  'David’s father has three sons: Snap, Crackle and _____?'];

var merchantAnswers = [{ option1: 'road', option2: 'street' }, { option1: 'cutlery' },
{ option1: 'hole', option2: 'cavity' },
{option1:'543'},
{option1:'anakin',option2:'skywalker',option3:'anakin skywalker'},
{option1:'mustafar'},
{option1:'red'},
{option1:'c-3po',option2:'c3po'},
{option1:'egg'},
{option1:'name',option2:'my name',option3:'your name'},
{option1:'David'}
];

var merchantRights = 0;

var gifts=[{
  name:'Darksaber',
  fragments:3,
  forcePower:10
}]

var items=[{
  name:'hackSystem',
  state:"",
  amount:0,
  info:'Found Vulnerabilities',
},{
  name:'specialMask',
  state:"",
  amount:0,
  info:'Decrease Bounty'
}]

var jediName; // your Jedi name
var forcePower = 34; // force power gained meditating at the Jedi Temple
var bountyValue = 0; // starts at 0, if it reaches 100 you get busted and lose the game
var fear = 50; // starts at 0, if it reaches 100 you join the Dark side and lose the game
var vulnerabilities = 0; // number of vulnerabilities found
var jediKnight = false; // boolean to indicate if the player has reached Jedi knight level
var teste
var hitpoints = 30 // your current hitpoints
var ancientTexts = [
  { chapter: 1, technique: '□Fo□□rc□e L□ea□p□□' },
  { chapter: 2, technique: 'T□ra□□n□□□s□fe□r F□o□rc□e□□' },
  { chapter: 3, technique: 'Fo□□rc□e B□urs□t' },
  { chapter: 4, technique: '□□M□alac□i□a' },
  { chapter: 5, technique: '□Fo□rce□□ We□a□□po□n' }
];
var sithLord = {
  name: 'Darth Tekman',
  hitpoints: 45,
  counterImmune: false
};

var welcomStage = engine.create({
  type: 'before',
  name: 'welcomStage'
});

welcomStage.executeBefore(function() {
  engine.showBanner('Welcom to code Wars');
});

welcomStage.addQuestion({
  type: 'input',
  message: 'What´s your name young Padawan',
  validator: function(answer) {
    if (answer === '' || (answer >= 0 && answer <= 9)) {//filtrar melhor os numeros
      return "I can't let people with that name on the force"
    }
  },
  action: function(answer) {
    console.log('Welcom ' + answer);
    jediName = answer;

  }
});
welcomStage.addQuestion({
  type: 'confirm',
  message: 'Are you sure you want to enter ?',
  action: function(answer) {
    if (answer) {
      console.log('May the Force be with you. Yong Padawan')

    } else {
      engine.quit

    }
  }
})
let jediTemple = engine.create({
  type: 'stage',
  name: 'jediTemple'

});
jediTemple.executeBefore(function() {
  engine.showBanner('Jedi Temple');
})
jediTemple.addQuestion({
  type: 'list',
  message: 'Chose the training',
  options: ['Conect with Force', 'Training with Master Obi-Wan Kenobi'],
  action: function(answer) {
    if (answer === 'Conect with Force') {
      let hours = prompt('How many hours you want stay contected with Force ? ');
      if (hours > 24) {
        console.log('The force corrupted you');
        console.log('You lose the game');
        engine.quit();
      } else {

        forcePowerObtain = Math.floor((hours * 2) - (fear / 2));

        if (forcePowerObtain > 35) {
          forcePower += 35;
          info.satageType1('Force Power',35,forcePower)
        

        } else if (forcePowerObtain < 0) {
          forcePower += 3;
          info.satageType1('Force Power',3,forcePower)
        } else {
          forcePower += forcePowerObtain;
          info.satageType1('Force Power',forcePowerObtain,forcePower)

        }
        darkSideTest(forcePower);
      }
    } else if (answer === 'Training with Master Obi-Wan Kenobi') {
      console.log('------Chose the weapon you want train---------');
      console.log('');
      console.log('sabre: The force doesn´t so affected by fear');
      console.log('');
      console.log('machingun: Your Obtain Force can be multiplied');
      console.log('');
      console.log('mindcontrol: You Obtain more Force , but you can lose too')
      console.log('');
      console.log('return: Return to main menu');
      weapon = prompt('Chose your weapon');
      if (weapon === 'sabre') {

        forcePowerObtain = (Math.floor(Math.random() * 45)) - (fear / 3)
        if (forcePowerObtain <= 0) {
          forcePowerObtain = 3
        }
        forcePower += forcePowerObtain;
        darkSideTest(forcePower)
        info.satageType1('Force Power',forcePowerObtain,forcePower)

      } else if (weapon === 'machingun') {
        let combo = Math.ceil(Math.random() * 3);
        forcePowerObtain = (15 * combo) - (fear / 2);
        if (forcePowerObtain <= 0) {
          forcePowerObtain = 3
        }
        forcePower += forcePowerObtain;
        darkSideTest(forcePower)
       info.satageType1('Force Power',forcePowerObtain,forcePower)
      } else if (weapon === 'mindcontrol') {
        let loseOrWin
        let randomForce = Math.floor(Math.random() * 60);
        if (randomForce < 15) {
          randomForce = 15
        }
        forcePowerObtain = randomForce - (fear / 2);
        loseOrWin = Math.floor(Math.random() * 3)
        if (forcePowerObtain <= 0) {
          forcePowerObtain = 5
        }
        console.log('lose or win: ' + loseOrWin)
        if (loseOrWin === 0) {//Lose
          forcePower = forcePower - Math.floor(forcePowerObtain / 2)
          console.log('Force Power Lose : ' + Math.floor(forcePowerObtain / 2));
          console.log('Total Force Power: ' + forcePower);

        } else if (loseOrWin === 1) {//win

          forcePower += Math.abs(forcePowerObtain)
          darkSideTest(forcePower)
          info.satageType1('Force Power',forcePowerObtain,forcePower)

        } else if (loseOrWin === 2) {//No win or Lose
        
          console.log('You dont Obtain Force Power')
          console.log('Total Force Power: ' + forcePower)
        }

      }
    } else if (answer === 'return') {
      return false
    }
  }

});




function darkSideTest(forcePower) {
  if (forcePower > 100) {
    console.log('The force corrupted you');
    console.log('You lose the game');
    engine.quit();
  }
}

var cantina = engine.create({ type: 'stage', name: 'Cantina' });

cantina.executeBefore(function() {
  engine.showBanner('Cantina');
})

cantina.executeBefore(function() {
  console.log('A merchant appeared')
})

cantina.addQuestion({
  type: 'confirm',
  message: 'Do you want to negotiate with him ?',
  action: function(answer) {
    if (answer) {
      console.log('Hey dear Friend');
      console.log('I have a offer to you ')
      console.log('But first you need answer me thre question´s')
    } else {
      console.log('No problem,see you later')
      return false
    }
  }
});


cantina.addQuestion({
  type: 'input',
  message: merchantQuestions[0],
  action: function(answer) {
    if (answer === merchantAnswers[0].option1 || answer === merchantAnswers[0].option2) {
      console.log('The question is right')
      merchantRights++
    } else {
      console.log('The question is wrong')
    }
  }
});
cantina.addQuestion({
  type: 'input',
  message: merchantQuestions[1],
  action: function(answer) {
    if (answer === merchantAnswers[1].option1) {
      console.log('The question is right')
      merchantRights++
    } else {
      console.log('The question is wrong')
    }
  }
});
cantina.addQuestion({
  type: 'input',
  message: merchantQuestions[2],
  action: function(answer) {
    if (answer === merchantAnswers[2].option1 || answer === merchantAnswers[2].option2) {

      console.log('The question is right')
      merchantRights++
     checkRightsQuestions()

    } else {
      console.log('The question is wrong')
    }
  }
});



function checkRightsQuestions(){
         
        if (merchantRights === 2) {

        console.log('Good Job!');
        console.log('You got two of my questions right');
        console.log('Now we can negotiate')

      } else if (merchantRights === 3) {

        console.log('Good Job!');
        console.log('You got three of my questions right');
        if(gifts[0].fragments<3){
        console.log('I will give you a gift');
        console.log('You received a Darksaber fragment');
        
        gifts[0].fragments++;
        console.log('You have '+gifts[0].fragments+'/3');
     
      if(gifts[0].fragments===3){
        console.log('Congratulations you gathered all the pieces of the DarkSaber');
        console.log('Go to the forge to rebuild it')
      }
       }
       console.log('Now we can negotiate')
      }else if(merchantRights<=1){
        console.log("it's a shame, good luck next time")
        return false
      }
      
merchantRights=0
}
cantina.addQuestion({
  type: 'list',
  message: 'Do you want decrease your bounty value or find vulnerabilities ',
  options: ['Decrease your bounty', 'Find vulnerabilities'],
  action: function(answer) {
    if (answer === 'Find vulnerabilities') {
      console.log('I have a hack system');
      console.log("Let's exchange favors");
      console.log("My pet was kidnapped by republic cruiser");
      console.log("Recover my pet and i give too you");
      console.log('You will earn bounty, but for a good cause');
      items[0].state='ready'
    } else if(answer === 'Decrease your bounty'){
      console.log('I have a special mask to that ');
      console.log("Let's exchange favors");
      console.log('A gang stole my precious cargo');
      console.log("Recover my cargo and i give too you");
      console.log('You will lose Force Power, but for a good cause');
       if(forcePower<=10){
        console.log('You dont have force Power to that')
        return false
      }
       items[1].state='ready'
    }

  }
});
cantina.addQuestion({
  type:'confirm',
  message:"Do you want start the mission? ",
  action:function(answer){
    if(answer){
    console.log('Right let´s go then')
    if( Math.floor(Math.random()*3)===0){
        console.log('Mission fail ');
        console.log('You almost die');
        console.log('Back to base');
        items[0].state='Not ready';
        items[1].state='Not ready';
        return false;
      }
    if(items[0].state==='ready'){
    cruiserMission (items[0].name);
    items[0].state='Not ready';

    }else if(items[1].state==='ready'){
    cruiserMission (items[1].name);
    items[1].state='Not ready';

    }
    
    }else{
    console.log('No problem,see you later');
     items[0].state='Not ready';
     items[1].state='Not ready';
    return false
    }
  }
})

function cruiserMission (missionType){
if(missionType==='hackSystem'){
console.log('You caught babi dragon');
console.log('Me dear babe')
console.log('Good job!!!');

bountyValue+=10
if(bountyValue>100){
  console.log('You are busted')
  console.log('You lose the game')
}
info.satageType1('Bounty Value',10,bountyValue);
items[0].amount++
info.satageType1('Hack Systems',1,items[0].amount)


}else if(missionType==='specialMask'){
console.log('You caught my cargo');
console.log('Good job!!!');
let count=0
forcePowerObtain=Math.floor(Math.random()*(35-10))+10;
if((forcePower-forcePowerObtain)<0){

  while((forcePower-forcePowerObtain)<0){
  count++;
  forcePower++;
  };
  forcePower=0
}else {
forcePower-=forcePowerObtain
}
  info.satageType2('Force Power',forcePowerObtain-count,forcePower);

}
items[1].amount++
info.satageType1('Special Mask',1,items[1].amount)
}

//vulnerabilities
/*vulnerabilitiesObtain = Math.floor(Math.random()*11 - ((fear/10) / 2));

if (vulnerabilitiesObtain<0){
vulnerabilitiesObtain=0;
}*/
engine.run();