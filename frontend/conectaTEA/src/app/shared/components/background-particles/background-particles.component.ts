// Libs
import { Component } from '@angular/core';
import { NgxParticlesModule } from '@tsparticles/angular';
import { MoveDirection, OutMode, Container } from '@tsparticles/engine';
import { loadSlim } from '@tsparticles/slim';
import { NgParticlesService } from '@tsparticles/angular';

@Component({
    selector: 'app-background-particles',
    imports: [NgxParticlesModule],
    templateUrl: './background-particles.component.html',
})
export class BackgroundParticlesComponent {
    public id: string = 'tsparticles';
    public particlesUrl: string = 'http://foo.bar/particles.json';

    particlesOptions = {
        fpsLimit: 120,
        interactivity: {
            events: {
                onClick: {
                    enable: true,
                },
                onHover: {
                    enable: true,
                    mode: 'repulse',
                },
                resize: {
                    enable: true,
                },
            },
            modes: {
                push: {
                    quantity: 4,
                },
                repulse: {
                    distance: 200,
                    duration: 0.4,
                },
            },
        },
        particles: {
            color: {
                value: '#ffffff',
            },
            links: {
                color: '#ffffff',
                distance: 150,
                enable: true,
                opacity: 0.5,
                width: 1,
            },
            move: {
                direction: MoveDirection.none,
                enable: true,
                outModes: {
                    default: OutMode.bounce,
                },
                random: false,
                speed: 6,
                straight: false,
            },
            number: {
                density: {
                    enable: true,
                    area: 800,
                },
                value: 200,
            },
            opacity: {
                value: 0.5,
            },
            shape: {
                type: 'circle',
            },
            size: {
                value: { min: 1, max: 5 },
            },
        },
        detectRetina: true,
    };

    constructor(private readonly ngParticlesService: NgParticlesService) {
        this.ngParticlesService.init(async (engine) => {
            await loadSlim(engine);
        });
    }

    public particlesLoaded(container: Container): void {
        console.log(container);
    }
}
