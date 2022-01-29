package com.example.coredemo.ui.measurements

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import be.appwise.core.ui.base.BaseViewModel
import be.appwise.measurements.Measurement
import be.appwise.measurements.units.UnitAngle
import be.appwise.measurements.units.UnitEnergy
import be.appwise.measurements.units.UnitLength

class MeasurementsViewModel : BaseViewModel() {

    // <editor-fold desc="Energy">
    val energyValue = MutableLiveData("0")
    val firstEnergyUnit = MutableLiveData(UnitEnergy.list.first())
    val secondEnergyUnit = MutableLiveData(UnitEnergy.list.first())

    val unitEnergyValue = MediatorLiveData<String>().also { mediator ->
        mediator.addSource(energyValue) {
            calculateEnergy(it.toDoubleOrNull(), firstEnergyUnit.value, secondEnergyUnit.value)
        }
        mediator.addSource(firstEnergyUnit) {
            calculateEnergy(energyValue.value?.toDouble(), it, secondEnergyUnit.value)
        }
        mediator.addSource(secondEnergyUnit) {
            calculateEnergy(energyValue.value?.toDouble(), firstEnergyUnit.value, it)
        }
    }

    private fun calculateEnergy(value: Double?, firstUnitEnergy: UnitEnergy?, secondUnitEnergy: UnitEnergy?) {
        if (value != null && firstUnitEnergy != null && secondUnitEnergy != null) {
            val measure = Measurement(value, firstUnitEnergy)
            val converted = measure.converted(secondUnitEnergy)
            val desc = converted.description
            unitEnergyValue.value = desc
        }
    }
    // </editor-fold>


    // <editor-fold desc="Length">
    val lengthValue = MutableLiveData("0")
    val firstLengthUnit = MutableLiveData(UnitLength.list.first())
    val secondLengthUnit = MutableLiveData(UnitLength.list.first())

    val unitLengthValue = MediatorLiveData<String>().also { mediator ->
        mediator.addSource(lengthValue) {
            calculateLength(it.toDoubleOrNull(), firstLengthUnit.value, secondLengthUnit.value)
        }
        mediator.addSource(firstLengthUnit) {
            calculateLength(lengthValue.value?.toDouble(), it, secondLengthUnit.value)
        }
        mediator.addSource(secondLengthUnit) {
            calculateLength(lengthValue.value?.toDouble(), firstLengthUnit.value, it)
        }
    }

    private fun calculateLength(value: Double?, firstUnitLength: UnitLength?, secondUnitLength: UnitLength?) {
        if (value != null && firstUnitLength != null && secondUnitLength != null) {
            val measure = Measurement(value, firstUnitLength)
            val converted = measure.converted(secondUnitLength)
            val desc = converted.description
            unitLengthValue.value = desc

            Measurement(10.0, UnitAngle.arcMinutes).unit
        }
    }
    // </editor-fold>
}