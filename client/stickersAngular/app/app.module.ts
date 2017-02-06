import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { CarteComponent }  from './carte.component';
import { EditImageComponent } from './editimage.component'
// import { ListeCarteComponent } from './components/liste-carte.component';
import { CarteListeComponent } from './components/carte-liste/carte-liste.component';
import {CarteService} from './services/carte.service';

@NgModule({
  imports:      [ 
  					BrowserModule,
  					RouterModule.forRoot([
  						{ path: 'home', component: HomeComponent},
  						{ path: 'cartes', component: CarteListeComponent},
						{
							path: '',
							redirectTo: '/home',
							pathMatch: 'full'
						}
						])
  				],
  declarations: [ 
  					AppComponent, 
  					NavbarComponent,
						CarteComponent,
						HomeComponent,
					EditImageComponent,
					CarteListeComponent
				],
  providers: [ CarteService ],				
  bootstrap:    [ AppComponent ]  

})
export class AppModule { }
