// En este archivo solo van a ir variables con constantes. 
import { toastDirectionGravity_enum, toastPosition_enum } from "./types.js";
// Config por defecto de la configuracion de toast.
export const CONFIG = {
    duration: 3000,
    gravity: toastDirectionGravity_enum.TOP,
    position: toastPosition_enum.RIGHT,
};
// Estilos de Toast:
export const TOAST_STYLES = {
    success: {
        background: "linear-gradient(to right,rgb(80, 204, 8), #96c93d)"
    },
    error: {
        background: "linear-gradient(to right, #ff0000, #ff6b6b)"
    }
};
export const inputName = ["id", "title", "description", "state", "endDate"];
export const inputNameAdd = ["title", "description", "endDate"];
