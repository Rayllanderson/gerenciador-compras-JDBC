import {CategoryCard} from "./categoryCard";


export default function CategoryList() {

    return (
        <div className={"container"}>
            <div style={{animation: 'appearFromBottom 1s'}}>

                <div className="row row-cols-1 row-cols-md-3 g-4" style={{maxWidth: 750, margin: '0 auto'}}>

                <CategoryCard id={'3'} />
                <CategoryCard id={'2'} />
                <CategoryCard id={'1'} />


                </div>
            </div>
        </div>
    )
}