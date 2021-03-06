import {ProductCardBody} from "../styles";
import {CardAddon} from "../CardAddon";
import {FiCheck, FiX} from "react-icons/all";
import {ProductContextInterface, ProductResponseBody} from "../../../interfaces/productInterface";
import {DeleteButton, EditButton} from "../../Buttons";
import React, {useContext} from "react";
import {VisibilityCardItemContext} from "../../../contexts/CardItemVisibilityContext";

interface Props {
    product: ProductResponseBody
    context:  React.Context<ProductContextInterface>
}

export function CardItem({product, context}: Props) {
    const {deleteButtonIsVisible, editButtonIsVisible} = useContext(VisibilityCardItemContext);
    const {setToEdit, setToRemove} = useContext(context);
    return (
        <ProductCardBody className={'loader'}>

            <div className={'addons'}>
                <CardAddon id={product.id} name={product.name}>
                    {editButtonIsVisible && <EditButton onClick={() => setToEdit(product)}/>}
                    {deleteButtonIsVisible && <DeleteButton onClick={() => setToRemove(product)}/>}
                </CardAddon>
            </div>

            <div className="body">
                <div className={'card-item text-center'}>
                    <h5 className="card-title">{product.name}</h5>
                </div>

                <div className={'card-item'}>
                    {
                        product.purchased ? (
                            <p className={'card-value bought'}><FiCheck title={'Produto comprado'}/></p>
                        ) : (
                            <p className={'card-value non-bought'}><FiX title={'Produto ainda não comprado'}/></p>
                        )
                    }
                </div>

                <div className={'card-item'}>
                    <p className={'card-value'}>R$ {product.stipulatedPrice} </p>
                </div>

                <div className={'card-item'}>
                    {product.purchased ? (
                            <>
                                <div>
                                    <p className={'card-value text-center'}> R$ {product.spentPrice} </p>
                                </div>
                            </>
                        ) :
                        <p><FiX title={'Ainda não comprado'}/></p>
                    }
                </div>
            </div>
        </ProductCardBody>
    )
}

