export class Carte{
    id : number;
    nom : string;
    prenom : string;
    email : string;
    titre : string;
    adresse : string;
    telephone : string;

    constructor(id: number, nom: string, prenom: string, email: string, titre: string, adresse: string, telephone: string){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.titre = titre;
        this.adresse = adresse;
        this.telephone = telephone;
    }

};