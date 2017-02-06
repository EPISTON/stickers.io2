import { Component, OnInit } from '@angular/core';
import { Carte } from './carte';
import { StyleCarte } from './stylecarte'
@Component({
    selector: 'my-carte',
    template: 
        `
            <h1>{{template}}</h1>
            <canvas id='myCanvas' width='600' height='400'></canvas>
        `
})
export class CarteComponent implements OnInit{
    template: string = "template";
    carte : Carte = { nom : "Viollet", prenom : "Benoit", adresse : "Rue Jean Bodin", email : "viollet.benoit@free.fr", telephone : "1234567890", titre : "Ma super carte", id : 1};
    styleCarte : StyleCarte = { id : 0, couleur : 'red', nom : 'standard', police : 'Arial', template : 0};
    image : HTMLImageElement;
    width : 600;
    height : 400;

    ngOnInit(){
        let _this = this;
        this.image = new Image();
        this.image.src = "/testimg.jpg";
        this.image.onload = function(event) {
            _this.applyCanvas();
        };
    }

    applyCanvas(){
        if (this.styleCarte.template == 0){
            let canvas = document.getElementById("myCanvas") as HTMLCanvasElement;
            let ctx = canvas.getContext("2d");
            ctx.drawImage(this.image,0,0,this.image.width,this.image.height,0,0,600,400);
            ctx.fillStyle = this.styleCarte.couleur;
            ctx.font = "30px "+this.styleCarte.police;
            ctx.strokeText(this.carte.titre,20,35);
            ctx.fillText(this.carte.nom +", " + this.carte.prenom, 10, 70);
            ctx.fillText(this.carte.adresse, 10, 105);
            ctx.fillText(this.carte.email, 10, 140);
            ctx.fillText(this.carte.telephone, 10, 175);
        }else if (this.styleCarte.template == 1){
            let canvas = document.getElementById("myCanvas") as HTMLCanvasElement;
            let ctx = canvas.getContext("2d");
            ctx.drawImage(this.image,0,0,this.image.width,this.image.height,0,0,600,400);
            ctx.fillStyle = this.styleCarte.couleur;
            ctx.font = "italic small-caps 30px "+this.styleCarte.police;
            ctx.strokeText(this.carte.titre,20,35);
            ctx.fillText(this.carte.nom +", " + this.carte.prenom, 10, 70);
            ctx.fillText(this.carte.adresse, 10, 105);
            ctx.fillText(this.carte.email, 10, 140);
            ctx.fillText(this.carte.telephone, 10, 175);
        }else{
            let canvas = document.getElementById("myCanvas") as HTMLCanvasElement;
            let ctx = canvas.getContext("2d");
            ctx.drawImage(this.image,0,0,this.image.width,this.image.height,0,0,600,400);
            ctx.fillStyle = this.styleCarte.couleur;
            ctx.font = "bold 30px "+this.styleCarte.police;
            ctx.strokeText(this.carte.titre,20,35);
            ctx.fillText(this.carte.nom +", " + this.carte.prenom, 10, 70);
            ctx.fillText(this.carte.adresse, 10, 105);
            ctx.fillText(this.carte.email, 10, 140);
            ctx.fillText(this.carte.telephone, 10, 175);
        }
    }

}