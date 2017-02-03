import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
// import { ListeCarteComponent } from './components/liste-carte.component';

@NgModule({
  imports:      [ 
  					BrowserModule,
  					RouterModule.forRoot([
  						{ path: 'home', component: HomeComponent},
  						//{ path: 'cartes', component: ListeCarteComponent},
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
					HomeComponent
				],
  bootstrap:    [ AppComponent ]  
})
export class AppModule { }
