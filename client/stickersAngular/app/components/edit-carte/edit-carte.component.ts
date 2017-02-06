import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';

import {Carte} from '../../carte';
import {CarteService} from '../../services/carte.service'


@Component({
    moduleId : module.id,
    selector: 'edit-carte',
    templateUrl: './edit-carte.component.html'
})
export class EditCarteComponent implements OnInit {
    editedCarte : Carte;
    currentId : number;

    constructor(private carteService: CarteService,
                private route: ActivatedRoute) {
        this.editedCarte = null;
        this.currentId = 0;
    }

    ngOnInit() {
       this.route.params
           .subscribe(params => this.currentId= params['id'])
        this.editedCarte = this.carteService.findByid(this.currentId);
    }


}