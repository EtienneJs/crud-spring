import { toast_config_type } from "../types.js";
declare const Toastify: any;

export function toastify(conf:toast_config_type) {
    Toastify(conf).showToast();
}