import React, {createContext, ReactNode, useCallback, useContext, useState} from 'react';
import {AuthContext} from "./AuthContext";
import {ToastContext} from "./ToastContext";
import {useHistory} from 'react-router-dom';
import {validateRegister} from "../validations/userValidation";
import {UserRegisterBody} from "../interfaces/userInterface";
import {getError, getValidationError} from "../utils/handleApiErros";
import {LoadingContext} from "./LoadingContex";

interface RegisterContextProviderProps {
    children: ReactNode;
}

interface RegisterContextData {
    name: string,
    username: string,
    password: string,
    register: () => void,
    handleUsernameChange: (e: React.ChangeEvent<HTMLInputElement>) => void,
    handlePasswordChange: (e: React.ChangeEvent<HTMLInputElement>) => void,
    handleNameChange: (e: React.ChangeEvent<HTMLInputElement>) => void,

}

export const RegisterContext = createContext<RegisterContextData>({} as RegisterContextData);

export function RegisterProvider({children}: RegisterContextProviderProps) {

    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [name, setName] = useState<string>('');

    const {signUp} = useContext(AuthContext);
    const {addToast} = useContext(ToastContext);
    const {setButtonToLoad, clearButtonLoading} = useContext(LoadingContext);

    const history = useHistory();

    const register = useCallback(async () => {
        const user: UserRegisterBody = {
            name: name,
            username: username,
            password: password,
        }
        setButtonToLoad();
        await validateRegister(user).then(async () => {
            await signUp({name, username, password}).then(() => {
                history.push('/')
                addToast({
                    type: 'success',
                    title: "Cadastro realizado com sucesso!",
                    description: "Você já pode realizar o seu login na aplicação.",
                });
            }).catch(err => {
                const message = !!getValidationError(err) ? getValidationError(err) : getError(err);
                addToast({
                    type: 'error',
                    title: 'Erro',
                    description: message
                });
            })
        }).catch(err => {
            addToast({
                type: 'error',
                title: 'Erro',
                description: err.message,
            });
        });
        clearButtonLoading();
    }, [name, username, password, signUp, addToast, history, setButtonToLoad, clearButtonLoading])

    const handleUsernameChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value)
    }, [])

    const handlePasswordChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value)
    }, [])

    const handleNameChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
        setName(e.target.value)
    }, [])

    return (
        <RegisterContext.Provider value={{
            username, password, name,
            register,
            handlePasswordChange, handleUsernameChange, handleNameChange,
        }}>
            {children}
        </RegisterContext.Provider>
    )
}
