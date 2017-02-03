import { Component } from '@angular/core';

class Todo {
  id: number;
  titre: string;
  contexte: string;
  priorite: number;

  constructor (id: number, titre: string, contexte: string, priorite: number) {
    this.id = id;
    this.titre = titre;
    this.contexte = contexte;
    this.priorite = priorite;
  }
}

@Component({
  selector: 'my-app',
  template: `<my-carte></my-carte>
            `,
})
export class AppComponent  {
}
