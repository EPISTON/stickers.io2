import { Component, OnInit } from '@angular/core';
import { Carte } from './carte';
import { StyleCarte } from './stylecarte'


@Component({
    moduleId: module.id,
    selector: 'my-editimage',
    templateUrl: 'editimage.component.html'
})
export class EditImageComponent implements OnInit{
    template: string = "template";

    ngOnInit(){
    }
}