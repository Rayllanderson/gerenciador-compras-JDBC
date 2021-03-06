import React, {useContext, useEffect} from "react";
import Search from "../components/Search";
import {ProductHeader} from "../components/Header/Product";
import {ButtonGroup} from "../components/ButtonsGroup";
import ProductList from "../components/Card/product/ProductList";
import {SelectItemsButtons} from "../components/ButtonsGroup/selectItemsButtons";
import {CyanSecondaryButton, RedButton, YellowButton} from "../components/Buttons/styles";
import {ProductModal} from "../components/Modal/product/ProductModal";
import {DeleteModal} from "../components/Modal/DeleteModal";
import {MyPagination} from "../components/Paginations/Pagination";
import ProductController from "../controllers/productController";
import {useParams} from "react-router-dom";
import {ProductContext} from "../contexts/ProductContext";
import {TransferModal} from "../components/Modal/product/TransferModal";
import {ActionModalContext} from "../contexts/ActionModalContext";
import {StatisticContext} from "../contexts/StatisticContext";

interface RouteParams {
    id: string
}

export default function ProductPage(){
    const params = useParams<RouteParams>();
    const {setToSave, setCurrentCategoryId, remove, selectedProduct, handleNewCategoryIdChange,
        copyProductsToAnotherCategory, moveProductsToAnotherCategory} = useContext(ProductContext);
    const {copyProductsAction, moveProductsAction, removeVariousProductsAction, openFilterProductModalAction} = useContext(ActionModalContext);
    const {fetchStatisticsFromCurrentCategory} = useContext(StatisticContext);

    useEffect(() => {
        setCurrentCategoryId(params.id);
        document.title = 'Produtos';
    }, [params.id, setCurrentCategoryId])

    const controller = new ProductController(params.id);
    return (
        <div style={{minHeight: '100vh'}}>

            <ProductHeader fetchStatisticFunction={fetchStatisticsFromCurrentCategory}/>

            <Search placeholder={'Procurar um produto...'}/>

            <ButtonGroup addAction={setToSave} filterAction={openFilterProductModalAction}/>

            <ProductList controller={controller} context={ProductContext}/>

            <MyPagination controller={controller}/>

            <SelectItemsButtons>
                <YellowButton className={'btn me-4'} title={'Mover produtos selecionados para outra categoria'}
                onClick={moveProductsAction}>  Mover  </YellowButton>
                <CyanSecondaryButton className={'btn '} title={'Copiar produtos selecionados para outra categoria'}
                onClick={copyProductsAction}>Copiar</CyanSecondaryButton>
                <RedButton className={'btn ms-4'} title={'Deletar selecionados'}
                onClick={removeVariousProductsAction}>Deletar </RedButton>
            </SelectItemsButtons>

            <ProductModal/>

            <DeleteModal text={`Você tem certeza que deseja excluir o produto ${selectedProduct.name}?`}
                         action={remove}/>

            <TransferModal copyAction={copyProductsToAnotherCategory} moveAction={moveProductsToAnotherCategory}
                           handleCategoryIdChange={handleNewCategoryIdChange}/>
        </div>
    )
}