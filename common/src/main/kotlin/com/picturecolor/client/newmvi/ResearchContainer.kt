package com.picturecolor.client.newmvi

import com.badoo.reaktive.observable.*
import com.badoo.reaktive.scheduler.computationScheduler
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import com.badoo.reaktive.subject.behavior.behaviorSubject
import com.badoo.reaktive.subject.publish.PublishSubject
import com.badoo.reaktive.subject.publish.publishSubject
import com.picturecolor.client.addOrReplace
import com.picturecolor.client.constants.*
import com.picturecolor.client.debugLog
import com.picturecolor.client.model.*
import kotlin.math.*

object ResearchContainer {

  //sliceSizesData listeners
  val sliceSizesDataObservable = behaviorSubject(initialResearchSlicesSizesData())
  val axialSliceNumberObservable = behaviorSubject(1)
  val frontalSliceNumberObservable = behaviorSubject(1)
  val sagittalSliceNumberObservable = behaviorSubject(1)

  //sliceSizedDataContainers
  val axialSlicesSizesDataObservable = behaviorSubject(initialSlicesSizeData())
  val frontalSlicesSizesDataObservable = behaviorSubject(initialSlicesSizeData())
  val sagittalSlicesSizesDataObservable = behaviorSubject(initialSlicesSizeData())

  val researchIdObservable: BehaviorSubject<Int> = behaviorSubject(-1)

  //menu
  val blackValueObservable = behaviorSubject(INITIAL_BLACK)
  val whiteValueObservable = behaviorSubject(INITIAL_WHITE)
  val blackValueListener = publishSubject<Double>()
  val whiteValueListener = publishSubject<Double>()

  val axialBlackAndWhiteObservable = behaviorSubject(INITIAL_BLACK to INITIAL_WHITE)
  val frontalBlackAndWhiteObservable = behaviorSubject(INITIAL_BLACK to INITIAL_WHITE)
  val sagittalBlackAndWhiteObservable = behaviorSubject(INITIAL_BLACK to INITIAL_WHITE)

  val axialContrastBrightnessListener = publishSubject<MouseMoveData>()
  val frontalContrastBrightnessListener = publishSubject<MouseMoveData>()
  val sagittalContrastBrightnessListener = publishSubject<MouseMoveData>()

  val axialContrastBrightnessEndListener = publishSubject<MouseData>()
  val frontalContrastBrightnessEndListener = publishSubject<MouseData>()
  val sagittalContrastBrightnessEndListener = publishSubject<MouseData>()

  val axialContentRatioObservable = behaviorSubject(1.0)
  val frontalContentRatioObservable = behaviorSubject(1.0)
  val sagittalContentRatioObservable = behaviorSubject(1.0)

  val gammaValueObservable = behaviorSubject(INITIAL_GAMMA)
  val mipMethodObservable = behaviorSubject(MIP_METHOD_TYPE_NO_MIP)
  val mipValueObservable = behaviorSubject(INITIAL_MIP_VALUE)

  val showMipValueObservable = publishSubject<Boolean>()

  val presetObservable: BehaviorSubject<Preset> = behaviorSubject(Preset.PRESET_LUNGS)

  val selectedAreaObservable = behaviorSubject<Int>(-1)
  //listeners for new painted Area
  val axialNewCircleObservable: PublishSubject<Circle> = publishSubject()
  val frontalNewCircleObservable: PublishSubject<Circle> = publishSubject()
  val sagittalNewCircleObservable: PublishSubject<Circle> = publishSubject()
  //newAreaToSaveNotifier
  val newAreaToSaveObservable: PublishSubject<AreaToSave> = publishSubject()
  val areaToUpdateObservable = publishSubject<SelectedArea>()

  //listener for new SeletedArea
  val newAreaObservable: PublishSubject<SelectedArea> = publishSubject()
  //selectedAreasContainer
  val areasObservable = behaviorSubject<List<SelectedArea>>(listOf())

  val selectAreaObservable = publishSubject<Int>()
  val deleteAreaObservable = publishSubject<Int>()
  val areaDeletedObservable = publishSubject<Int>()

  val axialCirclesObservable = behaviorSubject<List<CircleShape>>(listOf())
  val frontalCirclesObservable = behaviorSubject<List<CircleShape>>(listOf())
  val sagittalCirclesObservable = behaviorSubject<List<CircleShape>>(listOf())

  val axialMoveRectsObservable = behaviorSubject<List<MoveRect>>(listOf())
  val frontalMoveRectsObservable = behaviorSubject<List<MoveRect>>(listOf())
  val sagittalMoveRectsObservable = behaviorSubject<List<MoveRect>>(listOf())

  val axialLinesObservable = behaviorSubject(
    initialLines(
      SLYCE_TYPE_AXIAL
    )
  )
  val frontalLinesObservable = behaviorSubject(
    initialLines(
      SLYCE_TYPE_FRONTAL
    )
  )
  val sagittalLinesObservable = behaviorSubject(
    initialLines(
      SLYCE_TYPE_SAGITTAL
    )
  )

  val axialMouseDataObservable = publishSubject<MouseData>()
  val frontalMouseDataObservable = publishSubject<MouseData>()
  val sagittalMouseDataObservable = publishSubject<MouseData>()

  val axialPositionDataObservable = publishSubject<PositionData>()
  val frontalPositionDataObservable = publishSubject<PositionData>()
  val sagittalPositionDataObservable = publishSubject<PositionData>()

  val axialMouseMoveObservable = publishSubject<MouseMoveData>()
  val frontalMouseMoveObservable = publishSubject<MouseMoveData>()
  val sagittalMouseMoveObservable = publishSubject<MouseMoveData>()

  val axialMouseClickObservable = publishSubject<MouseClickData>()
  val frontalMouseClickObservable = publishSubject<MouseClickData>()
  val sagittalMouseClickObservable = publishSubject<MouseClickData>()

  val axialMouseDownObservable = publishSubject<MouseClickData>()
  val frontalMouseDownObservable = publishSubject<MouseClickData>()
  val sagittalMouseDownObservable = publishSubject<MouseClickData>()

  val deleteClickObservable = publishSubject<Boolean>()

  val mouseUpListener = publishSubject<MouseData>()

  val axialSliceNumberMoveObservable = publishSubject<Int>()
  val frontalSliceNumberMoveObservable = publishSubject<Int>()
  val sagittalSliceNumberMoveObservable = publishSubject<Int>()

  val areaIdDrag = behaviorSubject(-1)
  val currentMoveRectInDrag = behaviorSubject<MoveRect?>(null)

  val axialContainerSizeChangeListener = behaviorSubject(
    ContainerSizeModel(
      0,
      0
    )
  )
  val frontalContainerSizeChangeListener = behaviorSubject(
    ContainerSizeModel(
      0,
      0
    )
  )
  val sagittalContainerSizeChangeListener = behaviorSubject(
    ContainerSizeModel(
      0,
      0
    )
  )

  init {

    axialContainerSizeChangeListener.subscribe(onNext = {
      val heightRatio = it.height.toDouble() / axialSlicesSizesDataObservable.value.height
      val widthRatio = it.width.toDouble() / 512
      val value = min(heightRatio, widthRatio)
      axialContentRatioObservable.onNext(value)
    })

    frontalContainerSizeChangeListener.subscribe(onNext = {
      val heightRatio = it.height.toDouble() / frontalSlicesSizesDataObservable.value.height
      val widthRatio = it.width.toDouble() / 512
      frontalContentRatioObservable.onNext(min(heightRatio, widthRatio))
    })

    sagittalContainerSizeChangeListener.subscribe(onNext = {
      val heightRatio = it.height.toDouble() / sagittalSlicesSizesDataObservable.value.height
      val widthRatio = it.width.toDouble() / 512
      val min = min(heightRatio, widthRatio)
      sagittalContentRatioObservable.onNext(min)
    })

    selectAreaObservable
      .doOnBeforeNext { id ->
        areasObservable.value
          .firstOrNull { it.id == id }
          ?.let {
            axialSliceNumberObservable.onNext(round(it.z).toInt())
            frontalSliceNumberObservable.onNext(round(it.y).toInt())
            sagittalSliceNumberObservable.onNext(round(it.x).toInt())
          }
      }
      .subscribe(onNext = selectedAreaObservable::onNext)
    mipMethodObservable
      .subscribe(onNext = {
        if (it != MIP_METHOD_TYPE_NO_MIP)
          showMipValueObservable.onNext(true)
        else
          showMipValueObservable.onNext(false)
      })

    sliceSizesDataObservable
      .subscribe(onNext = ResearchContainer::initResearch)

    newAreaObservable
      .map { newArea ->
        areasObservable.value.toList().addOrReplace(newArea) { it.id == newArea.id }
      }
      .subscribe(onNext = areasObservable::onNext)

    areasObservable
      .subscribe(onNext = ::updateAreas)

    areaDeletedObservable
      .map { id ->
        areasObservable.value.filter { it.id != id }.toList()
      }
      .subscribe(onNext = areasObservable::onNext)

    axialSliceNumberObservable
      .subscribe(onNext = {
        updateAreas(areasObservable.value)
        updateLinesByAxial(it)
      })

    frontalSliceNumberObservable
      .subscribe(onNext = {
        updateAreas(areasObservable.value)
        updateLinesByFrontal(it)
      })

    sagittalSliceNumberObservable
      .subscribe(onNext = {
        updateAreas(areasObservable.value)
        updateLinesBySagittal(it)
      })

    merge(
      axialContentRatioObservable,
      frontalContentRatioObservable,
      sagittalContentRatioObservable
    )
      .subscribe(onNext = {
        updateLinesByAxial(axialSliceNumber = axialSliceNumberObservable.value)
        updateLinesByFrontal(frontalSliceNumber = frontalSliceNumberObservable.value)
        updateLinesBySagittal(sagittalSliceNumber = sagittalSliceNumberObservable.value)
      })

    axialMouseDataObservable
      .map {
        val height = axialSlicesSizesDataObservable.value.height
        val cutHorizontalRatio = sagittalSlicesSizesDataObservable.value.maxFramesSize.toDouble() / height
        val cutVerticalRatio = frontalSlicesSizesDataObservable.value.maxFramesSize.toDouble() / height
        val cutToScreenRation = axialContentRatioObservable.value
        PositionData(
          x = (it.x * cutHorizontalRatio / cutToScreenRation).toInt(),
          y = (it.y * cutVerticalRatio / cutToScreenRation).toInt(),
          z = axialSliceNumberObservable.value
        )
      }
      .subscribe(onNext = axialPositionDataObservable::onNext)

    frontalMouseDataObservable
      .map {
        val height = frontalSlicesSizesDataObservable.value.height
        val cutVerticalRatio = axialSlicesSizesDataObservable.value.maxFramesSize.toDouble() / height
        val cutToScreenRatio = frontalContentRatioObservable.value
        val z = it.y * cutVerticalRatio / cutToScreenRatio
        PositionData(
          x = (it.x / cutToScreenRatio).toInt(),
          y = frontalSliceNumberObservable.value,
          z = z.toInt()
        )
      }
      .subscribe(onNext = frontalPositionDataObservable::onNext)

    sagittalMouseDataObservable
      .map {
        val height = sagittalSlicesSizesDataObservable.value.height
        val cutToScreenRatio = sagittalContentRatioObservable.value
        val cutVerticalRatio = axialSlicesSizesDataObservable.value.maxFramesSize.toDouble() / height
        PositionData(
          x = sagittalSliceNumberObservable.value,
          y = (it.x / cutToScreenRatio).toInt(),
          z = (it.y * cutVerticalRatio / cutToScreenRatio).toInt()
        )
      }
      .subscribe(onNext = sagittalPositionDataObservable::onNext)

    axialNewCircleObservable
      .filter { it.radius > 0.0 }
      .map {
        val cutToScreenRatio = axialContentRatioObservable.value
        it.copy(
          centerX = it.centerX / cutToScreenRatio,
          centerY = it.centerY / cutToScreenRatio,
          radius = it.radius / cutToScreenRatio
        )
      }
      .map {
        AreaToSave(
          x = it.centerX,
          y = it.centerY,
          z = axialSliceNumberObservable.value.toDouble(),
          radius = it.radius,
          size = (it.radius * 2) * axialSlicesSizesDataObservable.value.pixelLength
        )
      }
      .subscribe(onNext = newAreaToSaveObservable::onNext)

    frontalNewCircleObservable
      .filter { it.radius > 0.0 }
      .map {
        val cutToScreenRatio = frontalContentRatioObservable.value
        it.copy(
          centerX = it.centerX / cutToScreenRatio,
          centerY = it.centerY / cutToScreenRatio,
          radius = it.radius / cutToScreenRatio
        )
      }
      .map {
        AreaToSave(
          x = it.centerX,
          y = frontalSliceNumberObservable.value.toDouble(),
          z = it.centerY * axialSlicesSizesDataObservable.value.maxFramesSize / frontalSlicesSizesDataObservable.value.height,
          radius = it.radius,
          size = (it.radius * 2) * frontalSlicesSizesDataObservable.value.pixelLength
        )
      }
      .subscribe(onNext = newAreaToSaveObservable::onNext)


    sagittalNewCircleObservable
      .filter { it.radius > 0.0 }
      .map {
        val cutToScreenRatio = sagittalContentRatioObservable.value
        it.copy(
          centerX = it.centerX / cutToScreenRatio,
          centerY = it.centerY / cutToScreenRatio,
          radius = it.radius / cutToScreenRatio
        )
      }
      .map {
        AreaToSave(
          x = sagittalSliceNumberObservable.value.toDouble(),
          y = it.centerX,
          z = it.centerY * axialSlicesSizesDataObservable.value.maxFramesSize / sagittalSlicesSizesDataObservable.value.height,
          radius = it.radius,
          size = (it.radius * 2) * sagittalSlicesSizesDataObservable.value.pixelLength
        )
      }
      .subscribe(onNext = newAreaToSaveObservable::onNext)

    presetObservable
      .subscribe(isThreadLocal = true,
        onNext = {
          val black: Double
          val white: Double
          when (it) {
            Preset.PRESET_SOFT_TISSUE -> {
              black = -140.0
              white = 260.0
            }
            Preset.PRESET_VESSELS -> {
              black = 0.0
              white = 600.0
            }
            Preset.PRESET_BONES -> {
              black = -500.0
              white = 1000.0
            }
            Preset.PRESET_BRAIN -> {
              black = 0.0
              white = 80.0
            }
            Preset.PRESET_LUNGS -> {
              black = -1150.0
              white = 350.0
            }
          }

          val blackAndWhite = black to white
          axialBlackAndWhiteObservable.onNext(blackAndWhite)
          frontalBlackAndWhiteObservable.onNext(blackAndWhite)
          sagittalBlackAndWhiteObservable.onNext(blackAndWhite)
          blackValueObservable.onNext(black)
          whiteValueObservable.onNext(white)

        })

    blackValueListener
      .doOnBeforeNext { blackValueObservable.onNext(it) }
      .debounce(300, computationScheduler)
      .subscribe(onNext = {
        axialBlackAndWhiteObservable.onNext(axialBlackAndWhiteObservable.value.copy(first = it))
        frontalBlackAndWhiteObservable.onNext(frontalBlackAndWhiteObservable.value.copy(first = it))
        sagittalBlackAndWhiteObservable.onNext(sagittalBlackAndWhiteObservable.value.copy(first = it))
      })

    whiteValueListener
      .doOnBeforeNext { whiteValueObservable.onNext(it) }
      .debounce(300, computationScheduler)
      .subscribe(onNext = {
        axialBlackAndWhiteObservable.onNext(axialBlackAndWhiteObservable.value.copy(second = it))
        frontalBlackAndWhiteObservable.onNext(frontalBlackAndWhiteObservable.value.copy(second = it))
        sagittalBlackAndWhiteObservable.onNext(sagittalBlackAndWhiteObservable.value.copy(second = it))
      })

    axialContrastBrightnessListener
      .subscribe(onNext = {
        val old = axialBlackAndWhiteObservable.value
        val black = old.first - it.deltaY - it.deltaX
        val white = old.second - it.deltaY + it.deltaX
        axialBlackAndWhiteObservable.onNext(black to white)
        blackValueObservable.onNext(black)
        whiteValueObservable.onNext(white)
      })
    frontalContrastBrightnessListener
      .subscribe(onNext = {
        val old = frontalBlackAndWhiteObservable.value
        val black = old.first - it.deltaY - it.deltaX
        val white = old.second - it.deltaY + it.deltaX
        frontalBlackAndWhiteObservable.onNext(black to white)
        blackValueObservable.onNext(black)
        whiteValueObservable.onNext(white)
      })
    sagittalContrastBrightnessListener
      .subscribe(onNext = {
        val old = sagittalBlackAndWhiteObservable.value
        val black = old.first - it.deltaY - it.deltaX
        val white = old.second - it.deltaY + it.deltaX
        sagittalBlackAndWhiteObservable.onNext(black to white)
        blackValueObservable.onNext(black)
        whiteValueObservable.onNext(white)
      })

    axialContrastBrightnessEndListener
      .subscribe(onNext = {
        frontalBlackAndWhiteObservable.onNext(axialBlackAndWhiteObservable.value)
        sagittalBlackAndWhiteObservable.onNext(axialBlackAndWhiteObservable.value)
      })

    frontalContrastBrightnessEndListener
      .subscribe(onNext = {
        axialBlackAndWhiteObservable.onNext(frontalBlackAndWhiteObservable.value)
        sagittalBlackAndWhiteObservable.onNext(frontalBlackAndWhiteObservable.value)
      })

    sagittalContrastBrightnessEndListener
      .subscribe(onNext = {
        axialBlackAndWhiteObservable.onNext(sagittalBlackAndWhiteObservable.value)
        frontalBlackAndWhiteObservable.onNext(sagittalBlackAndWhiteObservable.value)
      })

    axialSliceNumberMoveObservable
      .map {
        axialSliceNumberObservable.value + it
      }
      .filter { it > 0 && it < axialSlicesSizesDataObservable.value.maxFramesSize }
      .subscribe(onNext = axialSliceNumberObservable::onNext)

    frontalSliceNumberMoveObservable
      .map {
        frontalSliceNumberObservable.value + it
      }
      .filter { it > 0 && it < frontalSlicesSizesDataObservable.value.maxFramesSize }
      .subscribe(onNext = frontalSliceNumberObservable::onNext)

    sagittalSliceNumberMoveObservable
      .map {
        sagittalSliceNumberObservable.value + it
      }
      .filter { it > 0 && it < sagittalSlicesSizesDataObservable.value.maxFramesSize }
      .subscribe(onNext = sagittalSliceNumberObservable::onNext)

    axialMouseClickObservable
      .doOnBeforeNext { data ->
        if (data.altKey) {
          axialCirclesObservable.value
            .firstOrNull {
              data.y < it.y + it.radius && data.y > it.y - it.radius
                && data.x < it.x + it.radius && data.x > it.x - it.radius
            }
            ?.let { circle ->
              areasObservable.value
                .firstOrNull { it.id == circle.areaId }
                ?.let {
                  axialSliceNumberObservable.onNext(round(it.z).toInt())
                  frontalSliceNumberObservable.onNext(round(it.y).toInt())
                  sagittalSliceNumberObservable.onNext(round(it.x).toInt())
                }
            }
        }
      }
      .map { data ->
        axialCirclesObservable.value.firstOrNull { area ->
          val dist = sqrt((data.x - area.x).pow(2) + (data.y - area.y).pow(2))
          dist < area.radius
        }?.areaId ?: -1
      }
      .subscribe(onNext = selectedAreaObservable::onNext)

    frontalMouseClickObservable
      .doOnBeforeNext { data ->
        if (data.altKey) {
          frontalCirclesObservable.value
            .firstOrNull {
              data.y < it.y + it.radius && data.y > it.y - it.radius
                && data.x < it.x + it.radius && data.x > it.x - it.radius
            }
            ?.let { circle ->
              areasObservable.value
                .firstOrNull { it.id == circle.areaId }
                ?.let {
                  axialSliceNumberObservable.onNext(round(it.z).toInt())
                  frontalSliceNumberObservable.onNext(round(it.y).toInt())
                  sagittalSliceNumberObservable.onNext(round(it.x).toInt())
                }
            }
        }
      }
      .map { data ->
        frontalCirclesObservable.value.firstOrNull { area ->
          val dist = sqrt((data.x - area.x).pow(2) + (data.y - area.y).pow(2))
          dist < area.radius
        }?.areaId ?: -1
      }
      .subscribe(onNext = selectedAreaObservable::onNext)

    sagittalMouseClickObservable
      .doOnBeforeNext { data ->
        if (data.altKey) {
          sagittalCirclesObservable.value
            .firstOrNull {
              data.y < it.y + it.radius && data.y > it.y - it.radius
                && data.x < it.x + it.radius && data.x > it.x - it.radius
            }
            ?.let { circle ->
              areasObservable.value
                .firstOrNull { it.id == circle.areaId }
                ?.let {
                  axialSliceNumberObservable.onNext(round(it.z).toInt())
                  frontalSliceNumberObservable.onNext(round(it.y).toInt())
                  sagittalSliceNumberObservable.onNext(round(it.x).toInt())
                }
            }
        }
      }
      .map { data ->
        sagittalCirclesObservable.value.firstOrNull { area ->
          val dist = sqrt((data.x - area.x).pow(2) + (data.y - area.y).pow(2))
          dist < area.radius
        }?.areaId ?: -1
      }
      .subscribe(onNext = selectedAreaObservable::onNext)

    selectedAreaObservable
      .subscribe(onNext = { updateAreas(areasObservable.value) })

    deleteClickObservable
      .subscribe(onNext = {
        debugLog("delete click inside container")
        if (selectedAreaObservable.value != -1) {
          deleteAreaObservable.onNext(selectedAreaObservable.value)
        }
      })

    axialMouseDownObservable
      .filter { selectedAreaObservable.value != -1 }
      .subscribe { data ->
        val moveRect = axialMoveRectsObservable.value.firstOrNull {
          data.y > it.top && data.y < it.top + it.sideLength
            && data.x > it.left && data.x < it.left + it.sideLength
        }
        if (moveRect != null) {
          currentMoveRectInDrag.onNext(moveRect)
        } else {
          val moveCircle = axialCirclesObservable.value.firstOrNull {
            data.y < it.y + it.radius && data.y > it.y - it.radius
              && data.x < it.x + it.radius && data.x > it.x - it.radius
          }
          if (moveCircle != null) {
            areaIdDrag.onNext(moveCircle.areaId)
          }
        }
      }

    frontalMouseDownObservable
      .filter { selectedAreaObservable.value != -1 }
      .subscribe { data ->
        val moveRect = frontalMoveRectsObservable.value.firstOrNull {
          data.y > it.top && data.y < it.top + it.sideLength
            && data.x > it.left && data.x < it.left + it.sideLength
        }
        if (moveRect != null) {
          currentMoveRectInDrag.onNext(moveRect)
        } else {
          val moveCircle = frontalCirclesObservable.value.firstOrNull {
            data.y < it.y + it.radius && data.y > it.y - it.radius
              && data.x < it.x + it.radius && data.x > it.x - it.radius
          }
          if (moveCircle != null) {
            areaIdDrag.onNext(moveCircle.areaId)
          }
        }
      }

    sagittalMouseDownObservable
      .filter { selectedAreaObservable.value != -1 }
      .subscribe { data ->
        val moveRect = sagittalMoveRectsObservable.value.firstOrNull {
          data.y > it.top && data.y < it.top + it.sideLength
            && data.x > it.left && data.x < it.left + it.sideLength
        }
        if (moveRect != null) {
          currentMoveRectInDrag.onNext(moveRect)
        } else {
          val moveCircle = sagittalCirclesObservable.value.firstOrNull {
            data.y < it.y + it.radius && data.y > it.y - it.radius
              && data.x < it.x + it.radius && data.x > it.x - it.radius
          }
          if (moveCircle != null) areaIdDrag.onNext(moveCircle.areaId)
        }
      }

    axialMouseMoveObservable
      .filter { selectedAreaObservable.value != -1 }
      .subscribe { data ->
        val currentMoveRectInDrag = currentMoveRectInDrag.value
        val areaIdInDrag = areaIdDrag.value
        val deltaY = data.deltaY / axialContentRatioObservable.value
        val deltaX = data.deltaX / axialContentRatioObservable.value
        when {
          currentMoveRectInDrag != null -> areasObservable
            .value
            .firstOrNull { it.id == currentMoveRectInDrag.areaId }
            ?.let {
              when (currentMoveRectInDrag.type) {
                MoveRectType.TOP -> newAreaObservable.onNext(
                  it.copy(
                    radius = it.radius - deltaY,
                    size = (it.radius - deltaY) * 2 * axialSlicesSizesDataObservable.value.pixelLength
                  )
                )
                MoveRectType.BOTTOM -> newAreaObservable.onNext(
                  it.copy(
                    radius = it.radius + deltaY,
                    size = (it.radius + deltaY) * 2 * axialSlicesSizesDataObservable.value.pixelLength
                  )
                )
                MoveRectType.RIGHT -> newAreaObservable.onNext(
                  it.copy(
                    radius = it.radius + deltaX,
                    size = (it.radius + deltaX) * 2 * axialSlicesSizesDataObservable.value.pixelLength

                  )
                )
                MoveRectType.LEFT -> newAreaObservable.onNext(
                  it.copy(
                    radius = it.radius - deltaX,
                    size = (it.radius - deltaX) * 2 * axialSlicesSizesDataObservable.value.pixelLength

                  )
                )
              }
            }
          areaIdInDrag != -1 -> areasObservable
            .value
            .firstOrNull { it.id == areaIdInDrag }
            ?.let {
              newAreaObservable.onNext(it.copy(x = it.x + deltaX, y = it.y + deltaY))
            }
        }
      }

    frontalMouseMoveObservable
      .filter { selectedAreaObservable.value != -1 }
      .subscribe { data ->
        val currentMoveRectInDrag = currentMoveRectInDrag.value
        val areaIdInDrag = areaIdDrag.value
        val deltaY = data.deltaY / frontalContentRatioObservable.value
        val deltaX = data.deltaX / frontalContentRatioObservable.value
        if (currentMoveRectInDrag != null) {
          areasObservable
            .value
            .firstOrNull { it.id == currentMoveRectInDrag.areaId }
            ?.let {
              when (currentMoveRectInDrag.type) {
                MoveRectType.TOP -> newAreaObservable.onNext(
                  it.copy(
                    radius = it.radius - deltaY,
                    size = (it.radius - deltaY) * 2 * frontalSlicesSizesDataObservable.value.pixelLength
                  )
                )
                MoveRectType.BOTTOM -> newAreaObservable.onNext(
                  it.copy(
                    radius = it.radius + deltaY,
                    size = (it.radius + deltaY) * 2 * frontalSlicesSizesDataObservable.value.pixelLength
                  )
                )
                MoveRectType.RIGHT -> newAreaObservable.onNext(
                  it.copy(
                    radius = it.radius + deltaX,
                    size = (it.radius + deltaX) * 2 * frontalSlicesSizesDataObservable.value.pixelLength

                  )
                )
                MoveRectType.LEFT -> newAreaObservable.onNext(
                  it.copy(
                    radius = it.radius - deltaX,
                    size = (it.radius - deltaX) * 2 * frontalSlicesSizesDataObservable.value.pixelLength

                  )
                )
              }
            }
        } else if (areaIdInDrag != -1) {
          areasObservable
            .value
            .firstOrNull { it.id == areaIdInDrag }
            ?.let {
              newAreaObservable.onNext(it.copy(x = it.x + deltaX, z = it.z + deltaY))
            }
        }
      }

    sagittalMouseMoveObservable
      .filter { selectedAreaObservable.value != -1 }
      .subscribe { data ->
        val currentMoveRectInDrag = currentMoveRectInDrag.value
        val areaIdInDrag = areaIdDrag.value
        val deltaY = data.deltaY / sagittalContentRatioObservable.value
        val deltaX = data.deltaX / sagittalContentRatioObservable.value
        if (currentMoveRectInDrag != null) {
          areasObservable
            .value
            .firstOrNull { it.id == currentMoveRectInDrag.areaId }
            ?.let {
              when (currentMoveRectInDrag.type) {
                MoveRectType.TOP -> newAreaObservable.onNext(
                  it.copy(
                    radius = it.radius - deltaY,
                    size = (it.radius - deltaY) * 2 * sagittalSlicesSizesDataObservable.value.pixelLength
                  )
                )
                MoveRectType.BOTTOM -> newAreaObservable.onNext(
                  it.copy(
                    radius = it.radius + deltaY,
                    size = (it.radius + deltaY) * 2 * sagittalSlicesSizesDataObservable.value.pixelLength
                  )
                )
                MoveRectType.RIGHT -> newAreaObservable.onNext(
                  it.copy(
                    radius = it.radius + deltaX,
                    size = (it.radius + deltaX) * 2 * sagittalSlicesSizesDataObservable.value.pixelLength

                  )
                )
                MoveRectType.LEFT -> newAreaObservable.onNext(
                  it.copy(
                    radius = it.radius - deltaX,
                    size = (it.radius - deltaX) * 2 * sagittalSlicesSizesDataObservable.value.pixelLength

                  )
                )
              }
            }
        } else if (areaIdInDrag != -1) {
          areasObservable
            .value
            .firstOrNull { it.id == areaIdInDrag }
            ?.let {
              newAreaObservable.onNext(it.copy(y = it.y + deltaX, z = it.z + deltaY))
            }
        }
      }

    mouseUpListener
      .doOnBeforeNext {
        val rectInDragAreaSizeChange = currentMoveRectInDrag.value
        val areaIdInDrag = areaIdDrag.value
        when {
          rectInDragAreaSizeChange != null -> //нужно сохранить
            areasObservable.value
              .firstOrNull { it.id == rectInDragAreaSizeChange.areaId }
              ?.let {
                areaToUpdateObservable.onNext(it)
              }
          areaIdInDrag != -1 -> //нужно сохранить
            areasObservable.value
              .firstOrNull { it.id == areaIdInDrag }
              ?.let {
                areaToUpdateObservable.onNext(it)
              }
        }
      }
      .subscribe {
        areaIdDrag.onNext(-1)
        currentMoveRectInDrag.onNext(null)
      }
  }

  private fun initResearch(slicesSizesData: ResearchSlicesSizesData) {

    axialSlicesSizesDataObservable.onNext(slicesSizesData.axial)
    frontalSlicesSizesDataObservable.onNext(slicesSizesData.frontal)
    sagittalSlicesSizesDataObservable.onNext(slicesSizesData.sagittal)

    axialSliceNumberObservable.onNext(slicesSizesData.axial.maxFramesSize / 2)
    frontalSliceNumberObservable.onNext(slicesSizesData.frontal.maxFramesSize / 2)
    sagittalSliceNumberObservable.onNext(slicesSizesData.sagittal.maxFramesSize / 2)

    researchIdObservable.onNext(slicesSizesData.researchId)
    blackValueObservable.onNext(INITIAL_BLACK)
    whiteValueObservable.onNext(INITIAL_WHITE)
    gammaValueObservable.onNext(INITIAL_GAMMA)
    mipMethodObservable.onNext(MIP_METHOD_TYPE_NO_MIP)
    mipValueObservable.onNext(INITIAL_MIP_VALUE)
  }

  private fun updateAreas(list: List<SelectedArea>) {
    val axialCirclesList = mutableListOf<CircleShape>()
    val frontalCirclesList = mutableListOf<CircleShape>()
    val sagittalCirclesList = mutableListOf<CircleShape>()

    val axialMovableRectsList = mutableListOf<MoveRect>()
    val frontalMovableRectsList = mutableListOf<MoveRect>()
    val sagittalMovableRectsList = mutableListOf<MoveRect>()

    val selectedArea = areasObservable.value.firstOrNull { selectedAreaObservable.value == it.id }

    list.forEach { area ->

      val frontalAxialCoefficient = frontalSlicesSizesDataObservable.value.height.toDouble() / axialSlicesSizesDataObservable.value.maxFramesSize
      val sagittalAxialCoefficient = sagittalSlicesSizesDataObservable.value.height.toDouble() / axialSlicesSizesDataObservable.value.maxFramesSize

      val axialSliceNumber = axialSliceNumberObservable.value
      AreaToAxialCircleMapper
        .invoke(
          axialSliceNumber,
          area,
          frontalAxialCoefficient,
          selectedAreaObservable.value,
          axialContentRatioObservable.value
        )?.let { circle ->
          axialCirclesList.add(circle)
          circle
        }?.let { circle ->
          if (selectedArea != null && selectedArea.id == circle.areaId) {
            CircleToMoveRectsMapper.invoke(
              circle = circle,
              sideLength = 6.0,
              color = "#fff",
              h = abs(axialSliceNumber - round(area.z)) * frontalAxialCoefficient
            )
          } else {
            null
          }
        }?.let { listOfMovableRects ->
          axialMovableRectsList.addAll(listOfMovableRects)
        }

      val frontalSliceNumber = frontalSliceNumberObservable.value
      AreaToFrontalCircleMapper
        .invoke(
          frontalSliceNumber,
          area,
          frontalAxialCoefficient,
          selectedAreaObservable.value,
          frontalContentRatioObservable.value
        )?.let { circle ->
          frontalCirclesList.add(circle)
          circle
        }?.let { circle ->
          if (selectedArea != null && selectedArea.id == circle.areaId) {
            CircleToMoveRectsMapper.invoke(
              circle = circle,
              sideLength = 6.0,
              color = "#fff",
              h = abs(frontalSliceNumber - round(area.y))
            )
          } else {
            null
          }
        }?.let { listOfMovableRects ->
          frontalMovableRectsList.addAll(listOfMovableRects)
        }

      val sagittalSliceNumber = sagittalSliceNumberObservable.value
      AreaToSagittalCircleMapper
        .invoke(
          sagittalSliceNumber,
          area,
          sagittalAxialCoefficient,
          selectedAreaObservable.value,
          sagittalContentRatioObservable.value
        )?.let { circle ->
          sagittalCirclesList.add(circle)
          circle
        }?.let { circle ->
          if (selectedArea != null && selectedArea.id == circle.areaId) {
            CircleToMoveRectsMapper.invoke(
              circle = circle,
              sideLength = 6.0,
              color = "#fff",
              h = abs(sagittalSliceNumber - round(area.x))
            )
          } else {
            null
          }
        }?.let { listOfMovableRects ->
          sagittalMovableRectsList.addAll(listOfMovableRects)
        }
    }

    axialCirclesObservable.onNext(axialCirclesList)
    frontalCirclesObservable.onNext(frontalCirclesList)
    sagittalCirclesObservable.onNext(sagittalCirclesList)

    axialMoveRectsObservable.onNext(axialMovableRectsList)
    frontalMoveRectsObservable.onNext(frontalMovableRectsList)
    sagittalMoveRectsObservable.onNext(sagittalMovableRectsList)
  }

  private fun updateLinesByAxial(axialSliceNumber: Int) {
    val axialSliceNumberDouble = axialSliceNumber.toDouble()
    val coefficient = axialSliceNumberDouble / axialSlicesSizesDataObservable.value.maxFramesSize
    sagittalLinesObservable.onNext(
      sagittalLinesObservable.value.copy(
        horizontal = sagittalLinesObservable.value.horizontal.copy(
          value = (sagittalSlicesSizesDataObservable.value.height * coefficient * sagittalContentRatioObservable.value).toInt()
        )
      )
    )

    frontalLinesObservable.onNext(
      frontalLinesObservable.value.copy(
        horizontal = frontalLinesObservable.value.horizontal.copy(
          value = (frontalSlicesSizesDataObservable.value.height * coefficient * frontalContentRatioObservable.value).toInt()
        )
      )
    )
  }

  private fun updateLinesByFrontal(frontalSliceNumber: Int) {
    val frontalSliceNumberDouble = frontalSliceNumber.toDouble()
    val coefficient = frontalSliceNumberDouble / frontalSlicesSizesDataObservable.value.maxFramesSize
    axialLinesObservable.onNext(
      axialLinesObservable.value.copy(
        horizontal = axialLinesObservable.value.horizontal.copy(
          value = (axialSlicesSizesDataObservable.value.height * coefficient * axialContentRatioObservable.value).toInt()
        )
      )
    )

    sagittalLinesObservable.onNext(
      sagittalLinesObservable.value.copy(
        vertical = sagittalLinesObservable.value.vertical.copy(
          value = (frontalSlicesSizesDataObservable.value.maxFramesSize * coefficient * sagittalContentRatioObservable.value).toInt()
        )
      )
    )
  }

  private fun updateLinesBySagittal(sagittalSliceNumber: Int) {
    val doubleValue = sagittalSliceNumber.toDouble()
    val coefficient = doubleValue / sagittalSlicesSizesDataObservable.value.maxFramesSize
    axialLinesObservable.onNext(
      axialLinesObservable.value.copy(
        vertical = axialLinesObservable.value.vertical.copy(
          value = (sagittalSlicesSizesDataObservable.value.maxFramesSize * coefficient * axialContentRatioObservable.value).toInt()
        )
      )
    )

    frontalLinesObservable.onNext(
      frontalLinesObservable.value.copy(
        vertical = frontalLinesObservable.value.vertical.copy(
          value = (sagittalSlicesSizesDataObservable.value.maxFramesSize * coefficient * frontalContentRatioObservable.value).toInt()
        )
      )
    )
  }
}
