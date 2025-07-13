export type LoginType = {
    email: string,
    role: string,
    token: string
};

export type GenericResponseType<T> = {
    success: boolean,
    message: string,
    data: T | null
};