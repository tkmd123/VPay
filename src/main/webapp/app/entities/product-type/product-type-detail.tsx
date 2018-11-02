import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './product-type.reducer';
import { IProductType } from 'app/shared/model/product-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProductTypeDetail extends React.Component<IProductTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { productTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            ProductType [<b>{productTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="productTypeCode">Product Type Code</span>
            </dt>
            <dd>{productTypeEntity.productTypeCode}</dd>
            <dt>
              <span id="productTypeName">Product Type Name</span>
            </dt>
            <dd>{productTypeEntity.productTypeName}</dd>
            <dt>
              <span id="productTypeDesc">Product Type Desc</span>
            </dt>
            <dd>{productTypeEntity.productTypeDesc}</dd>
            <dt>
              <span id="productUDF1">Product UDF 1</span>
            </dt>
            <dd>{productTypeEntity.productUDF1}</dd>
            <dt>
              <span id="productUDF2">Product UDF 2</span>
            </dt>
            <dd>{productTypeEntity.productUDF2}</dd>
            <dt>
              <span id="productUDF3">Product UDF 3</span>
            </dt>
            <dd>{productTypeEntity.productUDF3}</dd>
          </dl>
          <Button tag={Link} to="/entity/product-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/product-type/${productTypeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ productType }: IRootState) => ({
  productTypeEntity: productType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProductTypeDetail);
