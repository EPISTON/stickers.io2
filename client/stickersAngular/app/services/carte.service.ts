
import {Injectable} from '@angular/core'
import {Carte} from '../carte';
import {BehaviorSubject, Observable} from 'rxjs/Rx';
//import {Http, Response} from '@angular/http';
const SAMPLE_CARTE : Carte[] = [
    new Carte(1, "PISTON", " ERIC ", "eric.piston@business.com", "PISTON & FRERES", "3 Rue des Jardins", "01 23 36 34 47"),
    new Carte(2, "JEREMY", " NOCERA ", "jnoce@sticker.com", "GEEK À VOS SERVICES", "25 Avenue de Valenton", "01 23 36 34 47"),
    new Carte(3, "DIALLO", " ABDOU ", "carte@sticker.com", "BUSINESS IT", "3 rue des jardins", "01 23 36 34 47"),
    new Carte(4, "DIALLO", "DIARAYE", "carte@sticker.com", "BUSINESS & TECHNOLOGY", "3 Rue des jardins", "01 23 36 34 47"),
    new Carte(5, "BERTRAND", "COTE", "carte@sticker.com", "VOTRE INGENIEUR IT", "3 Rue des jardins", "01 23 36 34 47"),
    new Carte(6, "DIALLO", " DIARI ", "carte@sticker.com", "BUSINESS", "3 rue des jardins", "01 23 36 34 47"),
];

@Injectable()
export class CarteService 
{

    private _cartes : Carte[];
    private _carteObservableBuilder : BehaviorSubject<Carte[]>;

    constructor() {
        this._cartes = SAMPLE_CARTE;
       
        this._carteObservableBuilder = 
            new BehaviorSubject<Carte[]>(this._cartes);
    }

    getCartes(): Observable<Carte[]>{
        return this._carteObservableBuilder.asObservable();
    }

    findByid(id: number) : Carte {
        return this._cartes.find(carte => carte.id == id);
    }

}