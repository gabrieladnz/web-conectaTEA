// Libs
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { Component } from '@angular/core';

@Component({
    selector: 'app-calendar',
    imports: [MatCardModule, MatDatepickerModule],
    templateUrl: './calendar.component.html',
    styleUrl: './calendar.component.scss',
})
export class CalendarComponent {}
