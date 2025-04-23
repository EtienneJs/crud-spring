export const validationActions = {
    MinCharacters: (field) => {
        if (field.value.length < 5) {
            return `Field '${field.name}' must have at least 5 characters.`;
        }
        return null;
    },
    Required: (field) => {
        if (!field.value.trim()) {
            return `Field '${field.name}' is required.`;
        }
        return null;
    },
};
