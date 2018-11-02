import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPayPartner } from 'app/shared/model/pay-partner.model';
import { getEntities as getPayPartners } from 'app/entities/pay-partner/pay-partner.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pay-partner-log.reducer';
import { IPayPartnerLog } from 'app/shared/model/pay-partner-log.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPayPartnerLogUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPayPartnerLogUpdateState {
  isNew: boolean;
  payPartnerId: string;
}

export class PayPartnerLogUpdate extends React.Component<IPayPartnerLogUpdateProps, IPayPartnerLogUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      payPartnerId: '0',
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

    this.props.getPayPartners();
  }

  saveEntity = (event, errors, values) => {
    values.payLogDate = new Date(values.payLogDate);

    if (errors.length === 0) {
      const { payPartnerLogEntity } = this.props;
      const entity = {
        ...payPartnerLogEntity,
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
    this.props.history.push('/entity/pay-partner-log');
  };

  render() {
    const { payPartnerLogEntity, payPartners, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vPayApp.payPartnerLog.home.createOrEditLabel">Create or edit a PayPartnerLog</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : payPartnerLogEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="pay-partner-log-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="payLogAmountLabel" for="payLogAmount">
                    Pay Log Amount
                  </Label>
                  <AvField id="pay-partner-log-payLogAmount" type="text" name="payLogAmount" />
                </AvGroup>
                <AvGroup>
                  <Label id="payLogTransRefLabel" for="payLogTransRef">
                    Pay Log Trans Ref
                  </Label>
                  <AvField id="pay-partner-log-payLogTransRef" type="text" name="payLogTransRef" />
                </AvGroup>
                <AvGroup>
                  <Label id="payLogUsernameLabel" for="payLogUsername">
                    Pay Log Username
                  </Label>
                  <AvField id="pay-partner-log-payLogUsername" type="text" name="payLogUsername" />
                </AvGroup>
                <AvGroup>
                  <Label id="payLogProductTypeCodeLabel" for="payLogProductTypeCode">
                    Pay Log Product Type Code
                  </Label>
                  <AvField id="pay-partner-log-payLogProductTypeCode" type="text" name="payLogProductTypeCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="payLogResuleCodeLabel" for="payLogResuleCode">
                    Pay Log Resule Code
                  </Label>
                  <AvField id="pay-partner-log-payLogResuleCode" type="text" name="payLogResuleCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="payLogResuleDescLabel" for="payLogResuleDesc">
                    Pay Log Resule Desc
                  </Label>
                  <AvField id="pay-partner-log-payLogResuleDesc" type="text" name="payLogResuleDesc" />
                </AvGroup>
                <AvGroup>
                  <Label id="payLogDateLabel" for="payLogDate">
                    Pay Log Date
                  </Label>
                  <AvInput
                    id="pay-partner-log-payLogDate"
                    type="datetime-local"
                    className="form-control"
                    name="payLogDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.payPartnerLogEntity.payLogDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="payLogUDF1Label" for="payLogUDF1">
                    Pay Log UDF 1
                  </Label>
                  <AvField id="pay-partner-log-payLogUDF1" type="text" name="payLogUDF1" />
                </AvGroup>
                <AvGroup>
                  <Label id="payLogUDF2Label" for="payLogUDF2">
                    Pay Log UDF 2
                  </Label>
                  <AvField id="pay-partner-log-payLogUDF2" type="text" name="payLogUDF2" />
                </AvGroup>
                <AvGroup>
                  <Label id="payLogUDF3Label" for="payLogUDF3">
                    Pay Log UDF 3
                  </Label>
                  <AvField id="pay-partner-log-payLogUDF3" type="text" name="payLogUDF3" />
                </AvGroup>
                <AvGroup>
                  <Label id="payLogUDF4Label" for="payLogUDF4">
                    Pay Log UDF 4
                  </Label>
                  <AvField id="pay-partner-log-payLogUDF4" type="text" name="payLogUDF4" />
                </AvGroup>
                <AvGroup>
                  <Label id="payLogUDF5Label" for="payLogUDF5">
                    Pay Log UDF 5
                  </Label>
                  <AvField id="pay-partner-log-payLogUDF5" type="text" name="payLogUDF5" />
                </AvGroup>
                <AvGroup>
                  <Label for="payPartner.id">Pay Partner</Label>
                  <AvInput id="pay-partner-log-payPartner" type="select" className="form-control" name="payPartner.id">
                    <option value="" key="0" />
                    {payPartners
                      ? payPartners.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/pay-partner-log" replace color="info">
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
  payPartners: storeState.payPartner.entities,
  payPartnerLogEntity: storeState.payPartnerLog.entity,
  loading: storeState.payPartnerLog.loading,
  updating: storeState.payPartnerLog.updating,
  updateSuccess: storeState.payPartnerLog.updateSuccess
});

const mapDispatchToProps = {
  getPayPartners,
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
)(PayPartnerLogUpdate);
