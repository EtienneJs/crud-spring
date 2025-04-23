// En este archivo solo van a ir variables con constantes. 

import { 
    toast_config_type,
    toastDirectionGravity_enum,
    toastPosition_enum,
    toastStyle_enum 
} from "./types.js";

// Config por defecto de la configuracion de toast.
export const CONFIG:toast_config_type = {
    duration: 3000,
    gravity: toastDirectionGravity_enum.TOP,
    position: toastPosition_enum.RIGHT,
};


// Estilos de Toast:

export const TOAST_STYLES:toastStyle_enum = {
    success: {
        background: "linear-gradient(to right,rgb(80, 204, 8), #96c93d)"
    },
    error: {
        background: "linear-gradient(to right, #ff0000, #ff6b6b)"
    }
};


export const inputName = ["id","title","description","state","endDate"] as const;
export const inputNameAdd = ["title","description","endDate"] as const;


