export interface EnvironmentData{
    production: boolean
}

export interface Config {
    deployEnv: 'local' | 'test' | 'stage' |'prod';
    version: 'demo' |'regular' | 'extended';
}
    
