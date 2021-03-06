import React, {createContext, ReactNode, useCallback, useContext, useState} from 'react';
import {UserResponseBody} from "../interfaces/userInterface";
import UserController from "../controllers/userController";
import {validateField} from "../validations/userValidation";
import {ToastContext} from "./ToastContext";
import {AlertContext} from "./AlertContext";
import {ModalContext} from "./ModalContext";
import {getError} from "../utils/handleApiErros";
import {LoadingContext} from "./LoadingContex";

interface AccountContextProviderProps {
    children: ReactNode;
}

interface AccountContextContextData {
    user: UserResponseBody,
    name: string,
    username: string,
    password: string,
    hasChangedImage: boolean,
    fetchUser: () => void;
    update: () => void;
    updatePassword: () => void;
    clearPassword: () => void;
    uploadFile: () => void;
    removeFile: () => void;
    clearPhoto: () => void;
    photo: string | Blob | undefined,
    setName: (value: string) => void;
    setUsername: (value: string) => void;
    handleNameChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleUsernameChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handlePasswordChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleImageChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

export const AccountContext = createContext<AccountContextContextData>({} as AccountContextContextData);

export function AccountProvider({children}: AccountContextProviderProps) {

    const [user, setUser] = useState<UserResponseBody>({} as UserResponseBody);
    const [name, setName] = useState<string>('');
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [photo, setPhoto] = useState<string | Blob>();
    const [hasChangedImage, setHasChangedImage] = useState<boolean>(false)

    const {addToast} = useContext(ToastContext);
    const {addAlert} = useContext(AlertContext);
    const {setToLoad, clearLoading} = useContext(LoadingContext);
    const {
        closeChangeDataModal,
        closeChangePasswordModal,
        openPreviewPhotoModal,
        closePreviewPhotoModal,
        closeConfirmModal
    } = useContext(ModalContext);
    const {setButtonToLoad, clearButtonLoading} = useContext(LoadingContext);

    const fetchUserData = useCallback(async () => {
        setToLoad();
        await new UserController().fetchUserData().then((response) => {
            setUser(response.data);
        }).catch(err => {
            addToast({
                type: "error",
                title: 'Algo de errado aconteceu',
                description: getError(err)
            })
        })
        clearLoading();
    }, [setUser, addToast, setToLoad, clearLoading])

    const handleNameChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
        setName(e.target.value);
    }, [])

    const handleUsernameChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
    }, [])

    const handlePasswordChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    }, [])

    const clearPassword = useCallback(() => setPassword(''), []);

    const update = useCallback(async () => {
        setButtonToLoad();
        await validateField(username, 'Username').then(async () => {
            await new UserController().updateData({username: username, name: name, email: ''})
                .then(() => {
                    addToast({
                        type: 'success',
                        title: 'Pronto',
                        description: 'Seus dados foram alterados!'
                    })
                    fetchUserData();
                    closeChangeDataModal();
                }).catch(err => {
                    addAlert(getError(err));
                })
        }).catch(err => {
            addAlert(err.message)
        })
        clearButtonLoading();
    }, [addToast, fetchUserData, name, username, addAlert, closeChangeDataModal, setButtonToLoad, clearButtonLoading])

    const updatePassword = useCallback(async () => {
        setButtonToLoad();
        await validateField(password, 'Senha').then(async () => {
            await new UserController().updatePassword(password)
                .then(() => {
                    addToast({
                        type: 'success',
                        title: 'Pronto',
                        description: 'Sua Senha foi alterada!'
                    })
                    closeChangePasswordModal();
                }).catch(err => {
                    addAlert(getError(err))
                })
        }).catch(err => {
            addAlert(err.message)
        })
        clearButtonLoading();
    }, [addToast, password, addAlert, closeChangePasswordModal, setButtonToLoad, clearButtonLoading])

    const handleImageChange = useCallback(async (e: any) => {
        const file = e.target.files[0];
        if (!!file) {
            setPhoto(file);
            await openPreviewPhotoModal();
            const target = document.getElementById('image');
            const previewPhoto = () => {
                let reader = new FileReader();
                reader.onloadend = function () {
                    // @ts-ignore
                    target.src = reader.result;
                };
                reader.readAsDataURL(file)
            }
            previewPhoto();
        }
    }, [openPreviewPhotoModal])

    const clearPhoto = useCallback(() => {
        const file = (document.getElementById('file')) as HTMLInputElement;
        if(file){
            file.value = '';
        }
    }, [])

    /**
     *  pra sumir o card de 'upload e remove'
     */
    const closeUploadCard = useCallback(() => {
       const profileImageCard = document.getElementById('profileImage');
       if (!!profileImageCard) {
           profileImageCard.click();
           return;
       }
       const defaultImageCard =  document.getElementById('defaultImage');
       if (!!defaultImageCard) defaultImageCard.click();
    }, [])

    const uploadFile = useCallback(async () => {
        const data = new FormData();
        data.append('file', photo as string | Blob);
        setButtonToLoad();
        await new UserController().uploadPhoto(data)
            .then(() => {
                closePreviewPhotoModal();
                addToast({
                    type: 'success',
                    title: 'Pronto',
                    description: 'Sua foto foi alterada!'
                })
                fetchUserData().then();
                setHasChangedImage(i => !i);
                closeUploadCard();
                clearPhoto();
            }).catch(err => addAlert(getError(err)));
        clearButtonLoading();
    }, [photo, closePreviewPhotoModal, addToast, fetchUserData, addAlert, closeUploadCard, clearPhoto, setButtonToLoad, clearButtonLoading])

    const removeFile = useCallback(async () => {
        setButtonToLoad();
        await new UserController().removePhoto()
            .then(() => {
                addToast({
                    type: 'success',
                    title: 'Pronto',
                    description: 'Sua foto foi removida!'
                });
                setHasChangedImage(i => !i);
                closeConfirmModal();
                fetchUserData().then();
                closeUploadCard();
                clearPhoto();
            }).catch(err => getError(err));
        clearButtonLoading();
    }, [setHasChangedImage, closeConfirmModal, fetchUserData, addToast, closeUploadCard, clearPhoto, clearButtonLoading, setButtonToLoad])

    return (
        <AccountContext.Provider value={{
            fetchUser: fetchUserData,
            user,
            handleUsernameChange,
            handleNameChange,
            name,
            username,
            setUsername,
            setName,
            handlePasswordChange,
            password,
            update,
            updatePassword,
            clearPassword,
            handleImageChange,
            uploadFile,
            hasChangedImage,
            removeFile,
            clearPhoto,
            photo
        }}>
            {children}
        </AccountContext.Provider>
    )
}

