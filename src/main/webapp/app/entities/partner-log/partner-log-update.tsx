import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPartner } from 'app/shared/model/partner.model';
import { getEntities as getPartners } from 'app/entities/partner/partner.reducer';
import { getEntity, updateEntity, createEntity, reset } from './partner-log.reducer';
import { IPartnerLog } from 'app/shared/model/partner-log.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPartnerLogUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPartnerLogUpdateState {
  isNew: boolean;
  partnerId: string;
}

export class PartnerLogUpdate extends React.Component<IPartnerLogUpdateProps, IPartnerLogUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      partnerId: '0',
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

    this.props.getPartners();
  }

  saveEntity = (event, errors, values) => {
    values.partnerLogDate = new Date(values.partnerLogDate);

    if (errors.length === 0) {
      const { partnerLogEntity } = this.props;
      const entity = {
        ...partnerLogEntity,
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
    this.props.history.push('/entity/partner-log');
  };

  render() {
    const { partnerLogEntity, partners, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="vPayApp.partnerLog.home.createOrEditLabel">Create or edit a PartnerLog</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : partnerLogEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="partner-log-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="partnerLogAmountLabel" for="partnerLogAmount">
                    Partner Log Amount
                  </Label>
                  <AvField id="partner-log-partnerLogAmount" type="text" name="partnerLogAmount" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerLogTransRefLabel" for="partnerLogTransRef">
                    Partner Log Trans Ref
                  </Label>
                  <AvField id="partner-log-partnerLogTransRef" type="text" name="partnerLogTransRef" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerLogUsernameLabel" for="partnerLogUsername">
                    Partner Log Username
                  </Label>
                  <AvField id="partner-log-partnerLogUsername" type="text" name="partnerLogUsername" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerLogProductTypeCodeLabel" for="partnerLogProductTypeCode">
                    Partner Log Product Type Code
                  </Label>
                  <AvField id="partner-log-partnerLogProductTypeCode" type="text" name="partnerLogProductTypeCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerLogResultCodeLabel" for="partnerLogResultCode">
                    Partner Log Result Code
                  </Label>
                  <AvField id="partner-log-partnerLogResultCode" type="text" name="partnerLogResultCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerLogResultDescLabel" for="partnerLogResultDesc">
                    Partner Log Result Desc
                  </Label>
                  <AvField id="partner-log-partnerLogResultDesc" type="text" name="partnerLogResultDesc" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerLogDateLabel" for="partnerLogDate">
                    Partner Log Date
                  </Label>
                  <AvInput
                    id="partner-log-partnerLogDate"
                    type="datetime-local"
                    className="form-control"
                    name="partnerLogDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.partnerLogEntity.partnerLogDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerLogUDF1Label" for="partnerLogUDF1">
                    Partner Log UDF 1
                  </Label>
                  <AvField id="partner-log-partnerLogUDF1" type="text" name="partnerLogUDF1" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerLogUDF2Label" for="partnerLogUDF2">
                    Partner Log UDF 2
                  </Label>
                  <AvField id="partner-log-partnerLogUDF2" type="text" name="partnerLogUDF2" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerLogUDF3Label" for="partnerLogUDF3">
                    Partner Log UDF 3
                  </Label>
                  <AvField id="partner-log-partnerLogUDF3" type="text" name="partnerLogUDF3" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerLogUDF4Label" for="partnerLogUDF4">
                    Partner Log UDF 4
                  </Label>
                  <AvField id="partner-log-partnerLogUDF4" type="text" name="partnerLogUDF4" />
                </AvGroup>
                <AvGroup>
                  <Label id="partnerLogUDF5Label" for="partnerLogUDF5">
                    Partner Log UDF 5
                  </Label>
                  <AvField id="partner-log-partnerLogUDF5" type="text" name="partnerLogUDF5" />
                </AvGroup>
                <AvGroup>
                  <Label for="partner.id">Partner</Label>
                  <AvInput id="partner-log-partner" type="select" className="form-control" name="partner.id">
                    <option value="" key="0" />
                    {partners
                      ? partners.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/partner-log" replace color="info">
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
  partners: storeState.partner.entities,
  partnerLogEntity: storeState.partnerLog.entity,
  loading: storeState.partnerLog.loading,
  updating: storeState.partnerLog.updating,
  updateSuccess: storeState.partnerLog.updateSuccess
});

const mapDispatchToProps = {
  getPartners,
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
)(PartnerLogUpdate);
