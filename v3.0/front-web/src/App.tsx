import GlobalStyle from "./styles/global";
import {BrowserRouter} from "react-router-dom";
import {ThemeProvider} from "styled-components";
import React from "react";
import useToggleTheme from "./hooks/useToggleTheme";
import {AuthProvider} from "./contexts/AuthContext";
import {LoginProvider} from "./contexts/LoginContext";
import Routes from "./routes";
import {ToastProvider} from "./contexts/ToastContext";
import {VisibilityCardItemProvider} from "./contexts/CardItemVisibilityContext";
import {CardItemActionProvider} from "./contexts/SelectedItemsContext";
import {RegisterProvider} from "./contexts/RegisterContext";
import {CategoryProvider} from "./contexts/CategoryContext";
import {PaginationProvider} from "./contexts/PaginationContext";
import {ProductProvider} from "./contexts/ProductContext";
import {ModalProvider} from "./contexts/ModalContext";
import {AlertProvider} from "./contexts/AlertContext";
import {GeneralProvider} from "./contexts/GeneralContex";
import {ConfirmModalProvider} from "./contexts/ActionModalContext";
import {ConfirmModal} from "./components/Modal/ConfirmModal";
import {StatisticProvider} from "./contexts/StatisticContext";
import {FilterModal} from "./components/Modal/FilterModal";
import {LogoutProvider} from "./contexts/LogoutContext";
import {AccountProvider} from "./contexts/AccountContext";
import {ProfileImageProvider} from "./contexts/ProfileImageContex";
import {LoadingProvider} from "./contexts/LoadingContex";
import {AllProductProvider} from "./contexts/AllProductContext";

function App() {
    const {theme} = useToggleTheme();

    return (
        <ThemeProvider theme={theme}>
            <div className="App">
                <BrowserRouter>
                    <LoadingProvider>
                        <ToastProvider>
                            <VisibilityCardItemProvider>
                                <CardItemActionProvider>
                                    <AuthProvider>
                                        <LoginProvider>
                                            <RegisterProvider>
                                                <PaginationProvider>
                                                    <ModalProvider>
                                                        <AlertProvider>
                                                            <AccountProvider>
                                                                <ProfileImageProvider>
                                                                    <GeneralProvider>
                                                                        <LogoutProvider>
                                                                            <CategoryProvider>
                                                                                <AllProductProvider>
                                                                                    <ProductProvider>
                                                                                        <StatisticProvider>
                                                                                            <ConfirmModalProvider>
                                                                                                <GlobalStyle/>
                                                                                                <Routes/>
                                                                                                <FilterModal/>
                                                                                                <ConfirmModal/>
                                                                                            </ConfirmModalProvider>
                                                                                        </StatisticProvider>
                                                                                    </ProductProvider>
                                                                                </AllProductProvider>
                                                                            </CategoryProvider>
                                                                        </LogoutProvider>
                                                                    </GeneralProvider>
                                                                </ProfileImageProvider>
                                                            </AccountProvider>
                                                        </AlertProvider>
                                                    </ModalProvider>
                                                </PaginationProvider>
                                            </RegisterProvider>
                                        </LoginProvider>
                                    </AuthProvider>
                                </CardItemActionProvider>
                            </VisibilityCardItemProvider>
                        </ToastProvider>
                    </LoadingProvider>
                </BrowserRouter>
            </div>
        </ThemeProvider>
    )
}

export default App;
