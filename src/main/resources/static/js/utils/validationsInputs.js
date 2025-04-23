import { validationActions } from "./validationsActions.js";
export const validationsInputs = (validations, field) => {
    const validates = validations.split(',');
    const errors = [];
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
};
