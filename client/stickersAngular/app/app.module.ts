import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import {FormsModule} from '@angular/forms';

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { CarteComponent }  from './carte.component';

import { EditCarteComponent } from './components/edit-carte/edit-carte.component';
import { EditImageComponent } from './editimage.component'
// import { ListeCarteComponent } from './components/liste-carte.component';
import { CarteListeComponent } from './components/carte-liste/carte-liste.component';
import {CarteService} from './services/carte.service';

@NgModule({
  imports:      [ 
  					BrowserModule,FormsModule,
  					RouterModule.forRoot([
  						{ path: 'home', component: HomeComponent},
						 { path: 'edit/:id', component: EditCarteComponent},
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
						EditCarteComponent,
					HomeComponent,
					EditImageComponent,
					CarteListeComponent
				],
  providers: [ CarteService ],				
  bootstrap:    [ AppComponent ]  

})
export class AppModule { }
