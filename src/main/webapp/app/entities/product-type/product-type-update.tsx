import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './product-type.reducer';
import { IProductType } from 'app/shared/model/product-type.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProductTypeUpdateState {
  isNew: boolean;
}

export class ProductTypeUpdate extends React.Component<IProductTypeUpdateProps, IProductTypeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { productTypeEntity } = this.props;
      const entity = {
        ...productTypeEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/product-type');
  };

  render() {
    const { productTypeEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vPayApp.productType.home.createOrEditLabel">Create or edit a ProductType</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : productTypeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="product-type-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="productTypeCodeLabel" for="productTypeCode">
                    Product Type Code
                  </Label>
                  <AvField
                    id="product-type-productTypeCode"
                    type="text"
                    name="productTypeCode"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="productTypeNameLabel" for="productTypeName">
                    Product Type Name
                  </Label>
                  <AvField
                    id="product-type-productTypeName"
                    type="text"
                    name="productTypeName"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="productTypeDescLabel" for="productTypeDesc">
                    Product Type Desc
                  </Label>
                  <AvField id="product-type-productTypeDesc" type="text" name="productTypeDesc" />
                </AvGroup>
                <AvGroup>
                  <Label id="productUDF1Label" for="productUDF1">
                    Product UDF 1
                  </Label>
                  <AvField id="product-type-productUDF1" type="text" name="productUDF1" />
                </AvGroup>
                <AvGroup>
                  <Label id="productUDF2Label" for="productUDF2">
                    Product UDF 2
                  </Label>
                  <AvField id="product-type-productUDF2" type="text" name="productUDF2" />
                </AvGroup>
                <AvGroup>
                  <Label id="productUDF3Label" for="productUDF3">
                    Product UDF 3
                  </Label>
                  <AvField id="product-type-productUDF3" type="text" name="productUDF3" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/product-type" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  productTypeEntity: storeState.productType.entity,
  loading: storeState.productType.loading,
  updating: storeState.productType.updating,
  updateSuccess: storeState.productType.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProductTypeUpdate);
