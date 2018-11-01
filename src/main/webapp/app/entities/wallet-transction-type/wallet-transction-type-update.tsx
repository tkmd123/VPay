import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './wallet-transction-type.reducer';
import { IWalletTransctionType } from 'app/shared/model/wallet-transction-type.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWalletTransctionTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IWalletTransctionTypeUpdateState {
  isNew: boolean;
}

export class WalletTransctionTypeUpdate extends React.Component<IWalletTransctionTypeUpdateProps, IWalletTransctionTypeUpdateState> {
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
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { walletTransctionTypeEntity } = this.props;
      const entity = {
        ...walletTransctionTypeEntity,
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
    this.props.history.push('/entity/wallet-transction-type');
  };

  render() {
    const { walletTransctionTypeEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vPayApp.walletTransctionType.home.createOrEditLabel">Create or edit a WalletTransctionType</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : walletTransctionTypeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="wallet-transction-type-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="walletTransTypeCodeLabel" for="walletTransTypeCode">
                    Wallet Trans Type Code
                  </Label>
                  <AvField id="wallet-transction-type-walletTransTypeCode" type="text" name="walletTransTypeCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="walletTransTypeNameLabel" for="walletTransTypeName">
                    Wallet Trans Type Name
                  </Label>
                  <AvField id="wallet-transction-type-walletTransTypeName" type="text" name="walletTransTypeName" />
                </AvGroup>
                <AvGroup>
                  <Label id="walletTransTypeDescLabel" for="walletTransTypeDesc">
                    Wallet Trans Type Desc
                  </Label>
                  <AvField id="wallet-transction-type-walletTransTypeDesc" type="text" name="walletTransTypeDesc" />
                </AvGroup>
                <AvGroup>
                  <Label id="walletTransTypeFlagLabel" for="walletTransTypeFlag">
                    Wallet Trans Type Flag
                  </Label>
                  <AvField
                    id="wallet-transction-type-walletTransTypeFlag"
                    type="string"
                    className="form-control"
                    name="walletTransTypeFlag"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/wallet-transction-type" replace color="info">
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
  walletTransctionTypeEntity: storeState.walletTransctionType.entity,
  loading: storeState.walletTransctionType.loading,
  updating: storeState.walletTransctionType.updating,
  updateSuccess: storeState.walletTransctionType.updateSuccess
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
)(WalletTransctionTypeUpdate);
