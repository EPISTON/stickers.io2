import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Rx';

import {Carte} from '../../carte';
import {CarteService} from '../../services/carte.service';


@Component({
    moduleId : module.id,
    selector: 'carte-liste',
    templateUrl: './carte-liste.component.html'
})
export class CarteListeComponent implements OnInit
 {
    private _cartes : Carte[];
    cartes : Observable<Carte[]>;
    
    // constructeur magique de typescript
    // ici, le mot clé private fait que l'attribut
    // sera automatiquement crée et affecté
    constructor(private _carteService: CarteService) {
        this.cartes = null;
        
    }

    ngOnInit() {
        this.cartes = this._carteService.getCartes();
        this.cartes.subscribe( carte => this._cartes = carte);

    }
 }