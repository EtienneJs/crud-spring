import { validationActions } from "./validationsActions.js";
export const validationsInputs = (validations:string, field:HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement) =>{
    const validates = validations.split(',');

    const errors: string[] = [];

    for (const rule of validates) {
        const action = validationActions[rule];
        if (action) {
            const errorMessage = action(field);
            if (errorMessage) {
                errors.push(errorMessage);
            }
        }
    }

    return errors;
}