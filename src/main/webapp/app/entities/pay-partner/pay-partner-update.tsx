import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './pay-partner.reducer';
import { IPayPartner } from 'app/shared/model/pay-partner.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPayPartnerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPayPartnerUpdateState {
  isNew: boolean;
}

export class PayPartnerUpdate extends React.Component<IPayPartnerUpdateProps, IPayPartnerUpdateState> {
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
      const { payPartnerEntity } = this.props;
      const entity = {
        ...payPartnerEntity,
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
    this.props.history.push('/entity/pay-partner');
  };

  render() {
    const { payPartnerEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vPayApp.payPartner.home.createOrEditLabel">Create or edit a PayPartner</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : payPartnerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="pay-partner-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="payPartnerCodeLabel" for="payPartnerCode">
                    Pay Partner Code
                  </Label>
                  <AvField
                    id="pay-partner-payPartnerCode"
                    type="text"
                    name="payPartnerCode"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="payPartnerNameLabel" for="payPartnerName">
                    Pay Partner Name
                  </Label>
                  <AvField
                    id="pay-partner-payPartnerName"
                    type="text"
                    name="payPartnerName"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="payPartnerDescLabel" for="payPartnerDesc">
                    Pay Partner Desc
                  </Label>
                  <AvField id="pay-partner-payPartnerDesc" type="text" name="payPartnerDesc" />
                </AvGroup>
                <AvGroup>
                  <Label id="payPartnerUDF1Label" for="payPartnerUDF1">
                    Pay Partner UDF 1
                  </Label>
                  <AvField id="pay-partner-payPartnerUDF1" type="text" name="payPartnerUDF1" />
                </AvGroup>
                <AvGroup>
                  <Label id="payPartnerUDF2Label" for="payPartnerUDF2">
                    Pay Partner UDF 2
                  </Label>
                  <AvField id="pay-partner-payPartnerUDF2" type="text" name="payPartnerUDF2" />
                </AvGroup>
                <AvGroup>
                  <Label id="payPartnerUDF3Label" for="payPartnerUDF3">
                    Pay Partner UDF 3
                  </Label>
                  <AvField id="pay-partner-payPartnerUDF3" type="text" name="payPartnerUDF3" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/pay-partner" replace color="info">
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
  payPartnerEntity: storeState.payPartner.entity,
  loading: storeState.payPartner.loading,
  updating: storeState.payPartner.updating,
  updateSuccess: storeState.payPartner.updateSuccess
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
)(PayPartnerUpdate);
