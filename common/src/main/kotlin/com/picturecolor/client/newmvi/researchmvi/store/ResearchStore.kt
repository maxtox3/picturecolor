package com.picturecolor.client.newmvi.researchmvi.store

import com.badoo.reaktive.disposable.Disposable
import com.badoo.reaktive.observable.Observable
import com.picturecolor.client.constants.CutsGridType
import com.picturecolor.client.model.AreaToSave
import com.picturecolor.client.model.GridModel
import com.picturecolor.client.model.ResearchSlicesSizesData
import com.picturecolor.client.model.SelectedArea

interface ResearchStore : Disposable {

  val states: Observable<State>

  fun accept(intent: Intent)

  data class State(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: ResearchSlicesSizesData? = null,
    val researchId: Int = 0,
    val gridModel: GridModel
  )

  sealed class Intent {
    data class Init(val researchId: Int) : Intent()
    class DeleteMark(val areaId: Int) : Intent()
    object DeleteCalled : Intent()
    class SaveMark(val areaToSave: AreaToSave) : Intent()
    class UpdateMark(val areaToUpdate: SelectedArea) : Intent()
    class ChangeGrid(val type: CutsGridType) : Intent()

    object DismissError : Intent()
  }
}