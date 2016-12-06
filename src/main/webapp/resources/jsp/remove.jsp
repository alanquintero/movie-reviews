<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- Modal -->
<div class="modal fade" id="modalRemove" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">
                      &times;
                  </span>
              </button>
              <h4 class="modal-title" id="removeModalLabel">
                  Remove
              </h4>
          </div>
          <div class="modal-body" id="confirmationMessage">
              Are you sure to delete?
          </div>
          <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">
                  Cancel
              </button>
              <a href="" class="btn btn-danger removeBtn">
                  Remove
              </a>
          </div>
      </div>
  </div>
</div>